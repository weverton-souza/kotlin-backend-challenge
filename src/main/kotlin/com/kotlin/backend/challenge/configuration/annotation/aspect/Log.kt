package com.kotlin.backend.challenge.configuration.annotation.aspect

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.introspect.Annotated
import com.fasterxml.jackson.databind.introspect.AnnotatedClass
import com.fasterxml.jackson.databind.introspect.AnnotatedField
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.ContextualSerializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.kotlin.backend.challenge.configuration.annotation.Loggable
import com.kotlin.backend.challenge.configuration.annotation.Mask
import jakarta.persistence.Entity
import java.lang.reflect.Method
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.CodeSignature
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

const val APP_NAME = "backend-challenge-application"
val LOGGER: Logger = LoggerFactory.getLogger(APP_NAME)

@Aspect
@Component
class Log {
    @Around("@annotation(com.kotlin.backend.challenge.configuration.annotation.Loggable)")
    @Throws(
        IllegalAccessException::class,
    )
    fun execute(joinPoint: ProceedingJoinPoint): Any? {
        val (method, annotation) = joinPoint.extractMeta()
        val objectClass: Class<*> = joinPoint.target.javaClass

        try {
            annotation as Loggable
            if (annotation.logParams) {
                val mapFormattedParams =
                    joinPoint.getParameters().map { (name, value) -> "$name=${value?.toMaskedJson()}" }
                LOGGER.info(
                    "class=${objectClass.simpleName}, method=${method.name}, params=$mapFormattedParams",
                )
            } else {
                LOGGER.info(
                    "class=${objectClass.simpleName}, method=${method.name}",
                )
            }

            val proceed = joinPoint.proceed(joinPoint.args)

            if (annotation.logResult) {
                val resultToLog = when (proceed) {
                    is Page<*> -> {
                        "Page[number=${proceed.number}, size=${proceed.size}, totalElements=${proceed.totalElements}, totalPages=${proceed.totalPages}]"
                    }
                    else -> {
                        if (annotation.formatResultAsJson) proceed?.toMaskedJson() else proceed
                    }
                }

                LOGGER.info(logResult(objectClass, method, resultToLog))
            } else {
                LOGGER.info("class=${objectClass.simpleName}, method=${method.name}, executed with success")
            }

            return proceed
        } catch (ex: Exception) {
            LOGGER.error(
                "class=${objectClass.simpleName}, method=${method.name},messageError=${ex.message}",
                ex,
            )
            throw ex
        }
    }

    private fun logResult(objectClass: Class<*>, method: Method, proceed: Any?): String =
        "class=${objectClass.simpleName}, method=${method.name},result=$proceed"

    fun JoinPoint.getParameters(): Map<String, Any?> {
        val fieldsToMask = this.getFieldNamesToMaskValue()
        val param: MutableMap<String, Any?> = HashMap()
        val paramValues = args
        val paramNames = (signature as CodeSignature).parameterNames
        for (i in paramNames.indices) {
            if (fieldsToMask.contains(paramNames[i])) {
                param[paramNames[i]] = "********"
                continue
            }
            param[paramNames[i]] = paramValues[i]
        }
        return param
    }

    private fun JoinPoint.getFieldNamesToMaskValue(): Array<String> {
        val (_, annotation: Annotation) = this.extractMeta()
        annotation as Loggable
        return annotation.maskedFields
    }
}

class MaskAnnotationIntrospector : JacksonAnnotationIntrospector() {
    override fun findSerializer(am: Annotated): Any? {
        am.apply {
            if (this is AnnotatedClass) {
                val fieldsToMask =
                    this.fields().filter { annotatedField -> annotatedField.hasAnnotation(Mask::class.java) }

                if (fieldsToMask.isNotEmpty()) {
                    val allOtherFields =
                        this.fields().filter { annotatedField -> !annotatedField.hasAnnotation(Mask::class.java) }
                    return MaskSensitiveFieldValueSerializer(fieldsToMask, allOtherFields)
                }
            }
        }
        return super.findSerializer(am)
    }
}

object JacksonMaskedExtension {
    val mapper: ObjectMapper by lazy {
        ObjectMapper()
            .setAnnotationIntrospector(MaskAnnotationIntrospector())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false)
            .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false)
            .registerModule(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerKotlinModule()
    }
}

fun Any.toMaskedJson(): String {

    return try {
        if (this.javaClass.isAnnotationPresent(Entity::class.java)) {
            val id = this.javaClass.methods.find { it.name == "getId" }?.invoke(this)
            return "Entity[id=$id]"
        }
        JacksonMaskedExtension.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)
    } catch (ex: Exception) {
        "Error to parse to json"
    }
}

private class MaskSensitiveFieldValueSerializer(
    val annotatedFields: List<AnnotatedField>,
    val allOtherFields: List<AnnotatedField>,
) : StdSerializer<Any>(Any::class.java), ContextualSerializer {

    override fun serialize(value: Any, generator: JsonGenerator, provider: SerializerProvider) {
        generator.prettyPrinter = MinimalPrettyPrinter()
        generator.writeStartObject(value)

        annotatedFields.map { annotatedField ->
            val maskedValue = annotatedField.getAnnotation(Mask::class.java).value
            val fieldName = annotatedField.name
            generator.writeStringField(fieldName, maskedValue)
        }

        val properties = value.javaClass.declaredFields

        properties.forEach { it.isAccessible = true }

        allOtherFields.map { annotatedField ->
            val fieldName = annotatedField.name
            val fieldValue: Any? = properties.find { it.name == fieldName }?.get(value)

            fieldValue?.let {
                if (it is String) {
                    generator.writeStringField(fieldName, it)
                } else {
                    generator.writeObjectField(fieldName, it)
                }
            } ?: generator.writeNullField(fieldName)
        }

        properties.forEach { it.isAccessible = false }

        generator.writeEndObject()
    }

    override fun createContextual(prov: SerializerProvider?, property: BeanProperty?): JsonSerializer<*> {
        return MaskSensitiveFieldValueSerializer(annotatedFields, allOtherFields)
    }
}

class PageSerializer : JsonSerializer<Page<*>>() {
    override fun serialize(value: Page<*>, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeNumberField("pageNumber", value.number)
        gen.writeNumberField("pageSize", value.size)
        gen.writeNumberField("totalPages", value.totalPages)
        gen.writeNumberField("totalElements", value.totalElements)
        gen.writeBooleanField("first", value.isFirst)
        gen.writeBooleanField("last", value.isLast)
        gen.writeBooleanField("empty", value.isEmpty)
        gen.writeStringField("content", "[...content omitted for logging...]")
        gen.writeEndObject()
    }
}

class PageSerializerModule : SimpleModule() {
    init {
        addSerializer(Page::class.java, PageSerializer())
    }
}

private fun JoinPoint.extractMeta(): Pair<Method, Annotation> {
    val signature = this.signature as MethodSignature
    val method: Method = signature.method
    val annotation: Annotation = method.getAnnotation(Loggable::class.java)
    return Pair(method, annotation)
}
