package com.kotlin.backend.challenge.service

import com.kotlin.backend.challenge.payload.response.ElectionPercentageResponse

interface ElectionService {
    fun calculatePercentages(
        totalVoters: Int,
        validVotes: Int,
        blankVotes: Int,
        nullVotes: Int
    ): ElectionPercentageResponse
}
