package com.kotlin.backend.challenge.service

interface LocaleService {
    fun invoke(code: String, vararg args: Any): String
}
