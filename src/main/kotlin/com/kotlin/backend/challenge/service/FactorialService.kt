package com.kotlin.backend.challenge.service

import java.math.BigInteger

interface FactorialService {
    fun calculate(n: Int): BigInteger
}
