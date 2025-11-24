package com.kotlin.backend.challenge.resource

import com.kotlin.backend.challenge.exception.ApplicationErrorResponse
import com.kotlin.backend.challenge.exception.ExceptionDetails
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.Objects
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMethod

@ApiResponses(
    value = [
        ApiResponse(
            responseCode = "500",
            description = "Erro Interno do Servidor. Um erro inesperado ocorreu no servidor.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionDetails::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "405",
            description = "Exceção de Validação. Método não permitido para o recurso solicitado.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        implementation = ExceptionDetails::class
                    )
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "ID Inválido Fornecido. O ID fornecido está em um formato inválido.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionDetails::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Campo(s) Inválido(s) Fornecido(s). Verifique os dados fornecidos.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ApplicationErrorResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Recurso Não Encontrado. O recurso solicitado não foi encontrado.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ExceptionDetails::class)
                )
            ]
        )
    ]
)
@CrossOrigin(
    origins = ["*"],
    methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS]
)
interface BaseResource {
    fun <T> BaseResource.responseEntity(body: T, status: HttpStatus = HttpStatus.OK): ResponseEntity<T> {
        return ResponseEntity.status(status).body(body)
    }

    fun retrievePageableParameter(parameters: Map<String, Any>): PageRequest {
        val pageNumber = if (Objects.isNull(parameters["pageNumber"]) || parameters["pageNumber"].toString().isBlank()) {
            0
        } else {
            (parameters["pageNumber"] as String).toInt()
        }
        val pageSize = if (Objects.isNull(parameters["pageSize"]) || parameters["pageSize"].toString().isBlank()) {
            15
        } else {
            (parameters["pageSize"] as String).toInt()
        }
        return PageRequest.of(pageNumber, pageSize)
    }
}
