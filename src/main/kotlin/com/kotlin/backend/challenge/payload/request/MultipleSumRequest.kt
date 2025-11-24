package com.kotlin.backend.challenge.payload.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@Schema(description = "Parâmetros para cálculo de soma de múltiplos")
data class MultipleSumRequest(
    @field:NotNull(message = "Limite é obrigatório")
    @field:Min(value = 1, message = "Limite deve ser maior que zero")
    @Schema(description = "Número limite (não incluso)", example = "10")
    val limit: Int,

    @field:NotEmpty(message = "A lista de números não pode estar vazia")
    @Schema(description = "Conjunto de números que terão seus múltiplos somados", example = "[3, 5]")
    val numbers: Set<Int>
)
