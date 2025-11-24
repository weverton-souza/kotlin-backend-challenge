package com.kotlin.backend.challenge.service.impl

import com.kotlin.backend.challenge.configuration.annotation.Loggable
import com.kotlin.backend.challenge.service.BubbleSortService
import org.springframework.stereotype.Service

@Service
@Loggable
class BubbleSortServiceImpl: BubbleSortService {
    override fun sort(array: IntArray): IntArray {
        val result = array.copyOf()
        val n = result.size

        for (i in 0 until n - 1) {
            var swapped = false

            for (j in 0 until n - i - 1) {
                if (result[j] > result[j + 1]) {
                    val temp = result[j]
                    result[j] = result[j + 1]
                    result[j + 1] = temp
                    swapped = true
                }
            }

            if (!swapped) break
        }

        return result
    }
}
