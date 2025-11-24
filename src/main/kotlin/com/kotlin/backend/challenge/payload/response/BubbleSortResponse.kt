package com.kotlin.backend.challenge.payload.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Resposta da ordenação Bubble Sort")
data class BubbleSortResponse(
    @Schema(
        description = "Array ordenado em ordem crescente",
        example = "[0, 1, 2, 3, 4, 5, 6, 7]"
    )
    val sortedArray: List<Int>
)