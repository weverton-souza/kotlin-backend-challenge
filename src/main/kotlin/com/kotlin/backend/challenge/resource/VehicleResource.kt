package com.kotlin.backend.challenge.resource

import com.kotlin.backend.challenge.payload.request.VehicleCreateRequest
import com.kotlin.backend.challenge.payload.request.VehicleUpdateRequest
import com.kotlin.backend.challenge.payload.response.VehicleResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import javax.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Vehicles", description = "Gerenciamento de veículos")
interface VehicleResource : BaseResource {
    @Operation(
        summary = "Criar novo veículo",
        description = "Cadastra um novo veículo no sistema"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Veículo criado com sucesso",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VehicleResponse::class)
                )]
            ),
        ]
    )
    fun create(
        @Parameter(description = "Dados do veículo")
        @RequestBody @Valid request: VehicleCreateRequest
    ): ResponseEntity<VehicleResponse>

    @Operation(
        summary = "Atualizar veículo",
        description = "Atualiza todos os dados de um veículo existente"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Veículo atualizado com sucesso",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VehicleResponse::class)
                )]
            )
        ]
    )
    fun update(
        @Parameter(description = "ID do veículo", example = "1")
        @PathVariable id: UUID,
        @Parameter(description = "Dados atualizados do veículo")
        @RequestBody @Valid request: VehicleUpdateRequest
    ): ResponseEntity<VehicleResponse>

    @Operation(
        summary = "Atualizar parcialmente veículo",
        description = "Atualiza apenas os campos informados de um veículo"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Veículo atualizado com sucesso",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VehicleResponse::class)
                )]
            )
        ]
    )
    fun partialUpdate(
        @Parameter(description = "ID do veículo", example = "1")
        @PathVariable id: UUID,
        @Parameter(description = "Dados para atualização parcial")
        @RequestBody @Valid request: VehicleUpdateRequest
    ): ResponseEntity<VehicleResponse>

    @Operation(
        summary = "Buscar veículo por ID",
        description = "Retorna um veículo específico pelo seu ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Veículo encontrado com sucesso",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VehicleResponse::class)
                )]
            )
        ]
    )
    fun findById(
        @Parameter(description = "ID do veículo", example = "1")
        @PathVariable id: UUID
    ): ResponseEntity<VehicleResponse>

    @Operation(
        summary = "Buscar veículos por filtros",
        description = "Retorna veículos filtrados por marca, ano e/ou cor"
    )
    @Parameters(
        value = [
            Parameter(
                name = "pageNumber",
                `in` = ParameterIn.QUERY,
                description = "Número da página",
                example = "0",
                required = false
            ),
            Parameter(
                name = "pageSize",
                `in` = ParameterIn.QUERY,
                description = "Número de elementos por página",
                example = "15",
                required = false
            ),
            Parameter(
                name = "parameters",
                `in` = ParameterIn.QUERY,
                description = "Parâmetros adicionais",
                required = false,
                hidden = true
            )
        ]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Veículos encontrados com sucesso",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VehicleResponse::class)
                )]
            )
        ]
    )
    fun findAll(
        @Parameter(description = "Parâmetros adicionais para busca", hidden = true)
        @RequestParam parameters: Map<String, Any>,
        @Parameter(description = "Marca do veículo", example = "Honda", required = false)
        @RequestParam(required = false) brand: String?,
        @Parameter(description = "Ano do veículo", example = "2023", required = false)
        @RequestParam(required = false) year: Int?,
        @Parameter(description = "Cor do veículo", example = "Preto", required = false)
        @RequestParam(required = false) color: String?
    ): ResponseEntity<Page<VehicleResponse>>

    @Operation(
        summary = "Deletar veículo",
        description = "Remove um veículo do sistema"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Veículo deletado com sucesso"
            )
        ]
    )
    fun delete(
        @Parameter(description = "ID do veículo", example = "1")
        @PathVariable id: UUID
    ): ResponseEntity<Unit>
}