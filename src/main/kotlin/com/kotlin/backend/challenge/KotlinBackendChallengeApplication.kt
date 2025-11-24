package com.kotlin.backend.challenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.web.config.EnableSpringDataWebSupport

@EnableJpaAuditing
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
class KotlinBackendChallengeApplication

fun main(args: Array<String>) {
	runApplication<KotlinBackendChallengeApplication>(*args)
}
