package com.kotlin.backend.challenge.exception

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

@Schema(description = "Custom error response for Backend Challenge Errors", hidden = true)
interface ExceptionDetails {
    @get:Schema(description = "HTTP status of the error response", example = "BAD_REQUEST")
    val status: HttpStatus

    @get:Schema(description = "Error code", example = "application.error.internal.server.error")
    val code: String

    @get:Schema(description = "Error details", example = "An error occurred.")
    val details: String

    @get:Schema(description = "Error message for developers", example = "InternalServerError")
    val developerHelper: DeveloperHelper?

    @get:Schema(description = "Date and time when the error occurred", example = "2023-07-16T10:49:47.150939")
    val timestamp: String
}

@Schema(description = "Error details for developers")
data class DeveloperHelper(
    @get:Schema(description = "Exception class", example = "java.lang.IllegalArgumentException")
    val exceptionClass: String,

    @get:Schema(description = "Exception message", example = "Invalid argument")
    val cause: String?,

    @get:Schema(description = "Exception stack trace", example = "java.lang.IllegalArgumentException: Invalid argument")
    val originClass: String?,

    @get:Schema(
        description = "Exception stack trace",
        example = "at com.kotlin.backend.challenge.resource.ExamResourceImpl.create(ExamResourceImpl.kt:32)"
    )
    val originLine: Int?
)
