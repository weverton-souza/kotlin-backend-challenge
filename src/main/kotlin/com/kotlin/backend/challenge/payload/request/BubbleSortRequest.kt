package com.kotlin.backend.challenge.payload.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty

@Schema(description = "Array de números para ordenação")
data class BubbleSortRequest(
    @field:NotEmpty(message = "Array não pode estar vazio")
    @Schema(description = "Array de inteiros a ser ordenado", example = "[5, 3, 2, 4, 7, 1, 0, 6]")
    val array: List<Int>
)
