package com.kotlin.backend.challenge.payload.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Resultado do cálculo de fatorial")
class FactorialResponse (
    @Schema(description = "Número calculado", example = "5")
    val number: Int,

    @Schema(description = "Fatorial calculado", example = "120")
    val factorial: Long
)
