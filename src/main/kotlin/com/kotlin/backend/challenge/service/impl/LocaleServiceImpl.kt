package com.kotlin.backend.challenge.service.impl

import com.kotlin.backend.challenge.configuration.annotation.Loggable
import com.kotlin.backend.challenge.service.LocaleService
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service

@Service
class LocaleServiceImpl(
    private val messageSource: MessageSource
) : LocaleService {
    @Loggable
    override fun invoke(code: String, vararg args: Any): String =
        this.messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
}
