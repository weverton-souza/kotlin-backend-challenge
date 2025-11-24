package com.kotlin.backend.challenge.payload.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Resultado da soma de múltiplos")
class MultipleSumResponse(
    @Schema(description = "Números múltiplos encontrados", example = "[3, 5, 6, 9]")
    val multiples: Set<Int>,

    @Schema(description = "Soma total dos múltiplos", example = "23")
    val sum: Long
)
