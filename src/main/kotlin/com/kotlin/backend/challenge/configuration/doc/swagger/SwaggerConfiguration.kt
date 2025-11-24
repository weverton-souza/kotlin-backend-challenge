package com.kotlin.backend.challenge.configuration.doc.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Kotlin Backend Challenge Application",
        version = "v1"
    ),
    servers = [Server(url = "http://localhost:8080/")]
)
class SwaggerConfiguration
