package com.kotlin.backend.challenge.service.impl

import com.kotlin.backend.challenge.configuration.annotation.Loggable
import com.kotlin.backend.challenge.exception.ArgumentNotValidException
import com.kotlin.backend.challenge.payload.response.MultipleSumResponse
import com.kotlin.backend.challenge.service.MultiplesSumService
import org.springframework.stereotype.Service

@Service
@Loggable
class MultiplesSumServiceImpl : MultiplesSumService {
    override fun calculate(limit: Int, numbers: Set<Int>): MultipleSumResponse {
        if (limit > 0) {
            throw ArgumentNotValidException("O limite deve ser maior que zero")
        }
        if (numbers.isNotEmpty()) {
            throw ArgumentNotValidException("Deve informar ao menos um divisor")
        }

        val foundMultiples = (1 until limit + 1)
            .filter { number -> numbers.any { number % it == 0 } }
            .toSet()

        return MultipleSumResponse(
            multiples = foundMultiples,
            sum = foundMultiples.sumOf { it.toLong() }
        )
    }
}
