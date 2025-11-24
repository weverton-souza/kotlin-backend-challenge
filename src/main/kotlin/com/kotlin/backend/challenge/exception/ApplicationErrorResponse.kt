package com.kotlin.backend.challenge.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.kotlin.backend.challenge.utils.MessageKeys
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@JsonInclude(Include.NON_NULL, content = Include.NON_EMPTY)
@Schema(description = "Detalhes sobre a resposta de erro da aplicação.")
data class ApplicationErrorResponse(
    @Schema(
        description = "Status HTTP da resposta",
        type = "string",
        example = "400 BAD_REQUEST"
    )
    override val status: HttpStatus,

    @Schema(
        description = "Detalhes sobre o erro ocorrido",
        type = "string",
        example = "O parâmetro fornecido é inválido."
    )
    override val details: String,

    @Schema(
        description = "Código do erro",
        type = "string",
        example = "HTTP_4XX_400_BAD_REQUEST"
    )
    override val code: String,

    @Schema(
        description = "Informações adicionais para ajudar os desenvolvedores",
        implementation = DeveloperHelper::class
    )
    override val developerHelper: DeveloperHelper?,

    @Schema(
        description = "Timestamp do momento em que o erro ocorreu",
        type = "string",
        example = "2023-07-12T12:34:56.789"
    )
    override val timestamp: String,

    @JsonInclude(Include.NON_NULL, content = Include.NON_EMPTY)
    @ArraySchema(schema = Schema(implementation = MethodArgumentNotValidDetails::class))
    @Schema(
        description = "Erros específicos de campos inválidos",
        type = "array"
    )
    val fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null
) : ExceptionDetails {

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    open class Builder {
        companion object {
            val LOGGER: Logger = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)
        }

        private var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        private var details: String = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
        private var code: String = MessageKeys.HTTP_4XX_400_BAD_REQUEST
        private var developerHelper: DeveloperHelper? = null
        private var timestamp: String = LocalDateTime.now().toString()
        private var fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null

        fun status(status: HttpStatus): Builder {
            this.status = status
            return this
        }

        fun details(details: String?): Builder {
            if (details != null) {
                this.details = details
            }
            return this
        }

        fun code(codeException: String?): Builder {
            if (codeException != null) {
                this.code = codeException.replace("[.-]".toRegex(), "_").uppercase()
            }
            return this
        }

        fun developerHelper(developerHelper: DeveloperHelper?): Builder {
            this.developerHelper = developerHelper
            return this
        }

        fun fieldErrors(fieldErrors: MutableCollection<MethodArgumentNotValidDetails>): Builder {
            this.fieldErrors = fieldErrors
            return this
        }

        fun build(): ResponseEntity<ExceptionDetails> {
            return this.buildExceptionMessage(
                this.status,
                this.details,
                this.code,
                this.developerHelper,
                this.timestamp,
                this.fieldErrors
            )
        }

        private fun buildExceptionMessage(
            status: HttpStatus,
            details: String,
            code: String,
            developerHelper: DeveloperHelper? = null,
            timestamp: String,
            fieldErrors: MutableCollection<MethodArgumentNotValidDetails>? = null
        ): ResponseEntity<ExceptionDetails> {
            return try {
                LOGGER.info(
                    """
                        [buildExceptionMessage] Mensagem de Erro: 
                        status: $status, 
                        detalhes: $details, developerHelper: $developerHelper, 
                        timestamp: $timestamp, status: $status
                    """.trimIndent()
                )

                ResponseEntity(
                    ApplicationErrorResponse(status, details, code, developerHelper, timestamp, fieldErrors),
                    status
                )
            } catch (ex: Exception) {
                LOGGER.info(
                    """
                        [buildExceptionMessage] Mensagem não encontrada: 
                        status: $status, 
                        detalhes: $details, developerMessage: $developerHelper, 
                        timestamp: $timestamp, status: $status
                    """.trimIndent()
                )
                ResponseEntity(
                    ApplicationErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        details,
                        code,
                        developerHelper,
                        timestamp,
                        fieldErrors
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
            }
        }
    }
}
