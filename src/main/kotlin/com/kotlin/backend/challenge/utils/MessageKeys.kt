package com.kotlin.backend.challenge.utils

object MessageKeys {
    const val HTTP_4XX_400_BAD_REQUEST = "http.4xx.400-bad-request"
    const val HTTP_4XX_401_UNAUTHORIZED = "http.4xx.401-unauthorized"
    const val HTTP_4XX_403_FORBIDDEN = "http.4xx.403-forbidden"
    const val HTTP_4XX_404_NOT_FOUND = "http.4xx.404-not-found"
    const val HTTP_4XX_409_CONFLICT = "http.4xx.409-conflict"
    const val HTTP_5XX_500_INTERNAL_SERVER_ERROR = "http.5xx.500-internal-server-error"

    const val APPLICATION_VALIDATION_CONSTRAINTS_MESSAGE = "application.validation.constraints.details"
    const val APPLICATION_VALIDATION_CONSTRAINTS_NOTBLANK_FIELD_MESSAGE =
        "application.validation.constraints.not-blank.field.message"
    const val APPLICATION_VALIDATION_CONSTRAINTS_SIZE_FIELD_MESSAGE =
        "application.validation.constraints.size.field.message"
    const val APPLICATION_VALIDATION_CONSTRAINTS_EMAIL_MESSAGE =
        "application.validation.constraints.email.field.message"
    const val APPLICATION_VALIDATION_CONSTRAINTS_NOTBLANK_ENUM_DETAILS =
        "application.validation.constraints.not-blank.enum.message"
    const val APPLICATION_VALIDATION_CONSTRAINTS_QUESTION_ALREADY_EXISTS =
        "application.validation.constraints.question.already-exists"
    const val APPLICATION_VALIDATION_CONSTRAINTS_PAST_OR_PRESENT_FIELD_MESSAGE =
        "application.validation.constraints.past-or-present.field.message"

    const val APPLICATION_SUBMISSION_CURRENT_TIME_CANNOT_BE_LESS_THAN_START_TIME =
        "application.submission.current-time.cannot.be.less.than.start.time"
    const val APPLICATION_SUBMISSION_EXAM_SUBMISSION_ALREADY_FINISHED =
        "application.submission.exam.submission.already-finished"
}
