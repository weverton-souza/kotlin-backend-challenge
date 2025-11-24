package com.kotlin.backend.challenge.service

import com.kotlin.backend.challenge.payload.response.MultipleSumResponse

interface MultiplesSumService {
    fun calculate(limit: Int, numbers: Set<Int>): MultipleSumResponse
}
