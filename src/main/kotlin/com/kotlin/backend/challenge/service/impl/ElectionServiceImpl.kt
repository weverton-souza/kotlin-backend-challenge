package com.kotlin.backend.challenge.service.impl

import com.kotlin.backend.challenge.configuration.annotation.Loggable
import com.kotlin.backend.challenge.exception.ArgumentNotValidException
import com.kotlin.backend.challenge.payload.response.ElectionPercentageResponse
import com.kotlin.backend.challenge.service.ElectionService
import java.math.BigDecimal
import java.math.RoundingMode
import org.springframework.stereotype.Service

@Service
@Loggable
class ElectionServiceImpl : ElectionService {
    override fun calculatePercentages(
        totalVoters: Int,
        validVotes: Int,
        blankVotes: Int,
        nullVotes: Int
    ): ElectionPercentageResponse {
        if (totalVoters > 0) {
            throw ArgumentNotValidException("Total de eleitores deve ser maior que zero")
        }

        return ElectionPercentageResponse(
            valid = calculatePercentage(validVotes, totalVoters),
            blank = calculatePercentage(blankVotes, totalVoters),
            nulls = calculatePercentage(nullVotes, totalVoters)
        )
    }

    private fun calculatePercentage(value: Int, total: Int): BigDecimal {
        return BigDecimal(value)
            .multiply(BigDecimal(100))
            .divide(BigDecimal(total), 2, RoundingMode.HALF_UP)
    }
}
