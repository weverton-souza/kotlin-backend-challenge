package com.kotlin.backend.challenge.resource

import com.kotlin.backend.challenge.exception.ApplicationErrorResponse
import com.kotlin.backend.challenge.exception.ExceptionDetails
import com.kotlin.backend.challenge.payload.request.BubbleSortRequest
import com.kotlin.backend.challenge.payload.request.ElectionPercentageRequest
import com.kotlin.backend.challenge.payload.request.MultipleSumRequest
import com.kotlin.backend.challenge.payload.response.BubbleSortResponse
import com.kotlin.backend.challenge.payload.response.ElectionPercentageResponse
import com.kotlin.backend.challenge.payload.response.FactorialResponse
import com.kotlin.backend.challenge.payload.response.MultipleSumResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Exercises", description = "Exercícios algorítmicos")
interface ExercisesResource : BaseResource {

    @Operation(
        summary = "Calcular percentuais eleitorais",
        description = "Calcula os percentuais de votos válidos, brancos e nulos em relação ao total de eleitores"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Percentuais calculados com sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ElectionPercentageResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Dados inválidos",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApplicationErrorResponse::class))]
            )
        ]
    )
    fun calculateElectionPercentages(
        @Parameter(description = "Dados eleitorais")
        @RequestBody @Valid request: ElectionPercentageRequest
    ): ResponseEntity<ElectionPercentageResponse>

    @Operation(
        summary = "Ordenar array com Bubble Sort",
        description = "Ordena um array de inteiros em ordem crescente usando o algoritmo Bubble Sort"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Array ordenado com sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = BubbleSortResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Array inválido",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApplicationErrorResponse::class))]
            )
        ]
    )
    fun bubbleSort(
        @Parameter(description = "Array para ordenação")
        @RequestBody @Valid request: BubbleSortRequest
    ): ResponseEntity<BubbleSortResponse>

    @Operation(
        summary = "Calcular fatorial",
        description = "Calcula o fatorial de um número usando recursão"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Fatorial calculado com sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = FactorialResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Número inválido",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ExceptionDetails::class))]
            )
        ]
    )
    fun calculateFactorial(
        @Parameter(description = "Número para calcular o fatorial", example = "5")
        @PathVariable number: Int
    ): ResponseEntity<FactorialResponse>

    @Operation(
        summary = "Soma de múltiplos",
        description = "Calcula a soma de todos os múltiplos dos divisores informados abaixo de um limite"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Soma calculada com sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = MultipleSumResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Dados inválidos",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ApplicationErrorResponse::class))]
            )
        ]
    )
    fun calculateMultiplesSum(
        @Parameter(description = "Parâmetros para cálculo")
        @RequestBody @Valid request: MultipleSumRequest
    ): ResponseEntity<MultipleSumResponse>
}