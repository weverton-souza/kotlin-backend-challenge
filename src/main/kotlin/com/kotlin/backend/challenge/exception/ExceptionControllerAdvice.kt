package com.kotlin.backend.challenge.exception

import com.kotlin.backend.challenge.service.LocaleService
import com.kotlin.backend.challenge.utils.MessageKeys
import io.swagger.v3.oas.annotations.Hidden
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponseException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException

@Hidden
@RestControllerAdvice
class ExceptionControllerAdvice(
    private val localeService: LocaleService
) {

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception,
        exchange: HttpServletRequest,
    ): ResponseEntity<ExceptionDetails> {
        val status = when (ex) {
            is IllegalArgumentException -> HttpStatus.BAD_REQUEST
            is ArgumentNotValidException -> HttpStatus.BAD_REQUEST
            is ErrorResponseException -> HttpStatus.valueOf(ex.statusCode.value())
            is ResourceNotFoundException -> HttpStatus.NOT_FOUND
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        val code = when (ex) {
            is IllegalArgumentException -> MessageKeys.HTTP_4XX_400_BAD_REQUEST
            is ArgumentNotValidException -> MessageKeys.HTTP_4XX_400_BAD_REQUEST
            is ErrorResponseException -> MessageKeys.HTTP_4XX_400_BAD_REQUEST
            is ResourceNotFoundException -> MessageKeys.HTTP_4XX_404_NOT_FOUND
            else -> MessageKeys.HTTP_5XX_500_INTERNAL_SERVER_ERROR
        }

        val details = when (ex) {
            is WebExchangeBindException -> handleFieldErrors(ex)
            else -> localeService.invoke(code)
        }

        return ApplicationErrorResponse.Companion.builder()
            .details(details)
            .code(code)
            .status(status)
            .developerHelper(developerHelper(ex))
            .build()
    }

    private fun handleFieldErrors(ex: WebExchangeBindException): String {
        ex.bindingResult.fieldErrors.map { fieldError ->
            val defaultMessage = fieldError.defaultMessage ?: ""
            val errorMessage = try {
                localeService.invoke(
                    defaultMessage,
                    fieldError.arguments?.getOrNull(2) ?: "",
                    fieldError.arguments?.getOrNull(1) ?: ""
                )
            } catch (e: Exception) {
                defaultMessage
            }
            MethodArgumentNotValidDetails(fieldError.field, errorMessage)
        }
        return localeService.invoke(MessageKeys.APPLICATION_VALIDATION_CONSTRAINTS_MESSAGE)
    }

    private fun developerHelper(ex: Exception) =
        DeveloperHelper(
            ex.javaClass.name,
            ex.cause?.message ?: ex.message ?: "",
            ex.stackTrace.firstOrNull()?.className ?: "",
            ex.stackTrace.firstOrNull()?.lineNumber ?: -1
        )
}
