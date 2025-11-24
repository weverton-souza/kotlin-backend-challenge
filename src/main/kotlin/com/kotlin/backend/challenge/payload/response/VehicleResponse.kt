package com.kotlin.backend.challenge.payload.response

import com.kotlin.backend.challenge.domain.Vehicle
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "Resposta com dados de um veículo")
data class VehicleResponse(
    @Schema(description = "ID do veículo", example = "1")
    val id: UUID,

    @Schema(description = "Nome/modelo do veículo", example = "Civic")
    val vehicle: String,

    @Schema(description = "Marca/fabricante", example = "Honda")
    val brand: String,

    @Schema(description = "Ano de fabricação", example = "2023")
    val year: Int,

    @Schema(description = "Descrição do veículo", example = "Sedan executivo com motor 2.0")
    val description: String?,

    @Schema(description = "Indica se foi vendido", example = "false")
    val sold: Boolean,

    @Schema(description = "Cor de veículo", example = "Vermelho")
    val color: String,

    @Schema(description = "Data de criação do registro", example = "2024-01-15T10:30:00")
    val createdAt: LocalDateTime,

    @Schema(description = "Data da última atualização", example = "2024-01-15T10:30:00")
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(vehicle: Vehicle): VehicleResponse = VehicleResponse(
            id = vehicle.id,
            vehicle = vehicle.vehicle,
            brand = vehicle.brand,
            year = vehicle.year,
            description = vehicle.description,
            sold = vehicle.sold,
            color = vehicle.color,
            createdAt = vehicle.createdAt,
            updatedAt = vehicle.updatedAt
        )
    }
}
