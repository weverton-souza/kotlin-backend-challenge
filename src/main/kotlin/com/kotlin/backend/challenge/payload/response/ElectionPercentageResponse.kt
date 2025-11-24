package com.kotlin.backend.challenge.payload.response

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Percentuais eleitorais em relação ao total de eleitores")
data class ElectionPercentageResponse(
    @Schema(
        description = "Percentual de votos válidos",
        example = "80.00"
    )
    val valid: BigDecimal,

    @Schema(
        description = "Percentual de votos brancos",
        example = "15.00"
    )
    val blank: BigDecimal,

    @Schema(
        description = "Percentual de votos nulos",
        example = "5.00"
    )
    val nulls: BigDecimal
)
