package com.kotlin.backend.challenge.service.impl

import com.kotlin.backend.challenge.configuration.annotation.Loggable
import com.kotlin.backend.challenge.exception.ArgumentNotValidException
import com.kotlin.backend.challenge.service.FactorialService
import java.math.BigInteger
import org.springframework.stereotype.Service

@Service
@Loggable
class FactorialServiceImpl: FactorialService {
    override fun calculate(n: Int): BigInteger {
        if (n < 0) {
            throw ArgumentNotValidException("Não é possível calcular fatorial de número negativo")
        }

        return when (n) {
            0, 1 -> BigInteger.ONE
            else -> BigInteger.valueOf(n.toLong()) * calculate(n - 1)
        }
    }
}
