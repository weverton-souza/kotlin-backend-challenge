package com.kotlin.backend.challenge.configuration.annotation

annotation class Loggable(
    val logParams: Boolean = true,
    val logResult: Boolean = true,
    val formatResultAsJson: Boolean = true,
    val maskedFields: Array<String> = ["brand"]
)
