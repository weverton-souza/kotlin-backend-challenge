package com.kotlin.backend.challenge.payload.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

@Schema(description = "Dados eleitorais para cálculo de percentuais")
data class ElectionPercentageRequest(
    @field:NotNull(message = "Total de eleitores é obrigatório")
    @field:Min(value = 1, message = "Total de eleitores deve ser maior que zero")
    @Schema(description = "Total de eleitores", example = "1000")
    val totalVoters: Int,

    @field:NotNull(message = "Votos válidos é obrigatório")
    @field:Min(value = 0, message = "Votos válidos não pode ser negativo")
    @Schema(description = "Quantidade de votos válidos", example = "800")
    val validVotes: Int,

    @field:NotNull(message = "Votos brancos é obrigatório")
    @field:Min(value = 0, message = "Votos brancos não pode ser negativo")
    @Schema(description = "Quantidade de votos brancos", example = "150")
    val blankVotes: Int,

    @field:NotNull(message = "Votos nulos é obrigatório")
    @field:Min(value = 0, message = "Votos nulos não pode ser negativo")
    @Schema(description = "Quantidade de votos nulos", example = "50")
    val nullVotes: Int
)

