package com.kotlin.backend.challenge.resource.impl

import com.kotlin.backend.challenge.payload.request.BubbleSortRequest
import com.kotlin.backend.challenge.payload.request.ElectionPercentageRequest
import com.kotlin.backend.challenge.payload.request.MultipleSumRequest
import com.kotlin.backend.challenge.payload.response.BubbleSortResponse
import com.kotlin.backend.challenge.payload.response.ElectionPercentageResponse
import com.kotlin.backend.challenge.payload.response.FactorialResponse
import com.kotlin.backend.challenge.payload.response.MultipleSumResponse
import com.kotlin.backend.challenge.resource.ExercisesResource
import com.kotlin.backend.challenge.service.BubbleSortService
import com.kotlin.backend.challenge.service.ElectionService
import com.kotlin.backend.challenge.service.FactorialService
import com.kotlin.backend.challenge.service.MultiplesSumService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/exercises")
class ExercisesResourceImpl(
    private val electionService: ElectionService,
    private val bubbleSortService: BubbleSortService,
    private val factorialService: FactorialService,
    private val multiplesSumService: MultiplesSumService
) : ExercisesResource {

    @PostMapping("/election-percentages")
    override fun calculateElectionPercentages(
        @RequestBody request: ElectionPercentageRequest
    ): ResponseEntity<ElectionPercentageResponse> {
        val result = electionService.calculatePercentages(
            totalVoters = request.totalVoters,
            validVotes = request.validVotes,
            blankVotes = request.blankVotes,
            nullVotes = request.nullVotes
        )

        return responseEntity(
            ElectionPercentageResponse(
                valid = result.valid,
                blank = result.blank,
                nulls = result.nulls
            )
        )
    }

    @PostMapping("/bubble-sort")
    override fun bubbleSort(
        @RequestBody request: BubbleSortRequest
    ): ResponseEntity<BubbleSortResponse> {
        val sorted = bubbleSortService.sort(request.array.toIntArray())
        return responseEntity(BubbleSortResponse(sortedArray = sorted.toList()))
    }

    @GetMapping("/factorial/{number}")
    override fun calculateFactorial(
        @PathVariable number: Int
    ): ResponseEntity<FactorialResponse> {
        val result = factorialService.calculate(number)
        return responseEntity(FactorialResponse(number = number, factorial = result.toLong()))
    }

    @PostMapping("/multiples-sum")
    override fun calculateMultiplesSum(
        @RequestBody request: MultipleSumRequest
    ): ResponseEntity<MultipleSumResponse> {
        val response = multiplesSumService.calculate(request.limit, request.numbers)

        return responseEntity(response)
    }
}