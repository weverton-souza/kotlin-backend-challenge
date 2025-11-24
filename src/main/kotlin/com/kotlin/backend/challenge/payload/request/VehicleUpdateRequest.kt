package com.kotlin.backend.challenge.payload.request

import com.kotlin.backend.challenge.domain.Vehicle
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Schema(description = "Dados para atualização parcial de um veículo")
data class VehicleUpdateRequest(
    @field:Size(max = 255, message = "O nome do veículo deve ter no máximo 255 caracteres")
    @Schema(description = "Nome/modelo do veículo", example = "Civic")
    val vehicle: String? = null,

    @field:Size(max = 100, message = "A marca deve ter no máximo 100 caracteres")
    @Schema(description = "Marca/fabricante", example = "Honda")
    val brand: String? = null,

    @field:Min(value = 1886, message = "O ano deve ser maior ou igual a 1886")
    @field:Max(value = 2026, message = "O ano não pode ser maior que o próximo ano")
    @Schema(description = "Ano de fabricação", example = "2023")
    val year: Int? = null,

    @Schema(description = "Descrição do veículo", example = "Sedan executivo com motor 2.0")
    val description: String? = null,

    @Schema(description = "Indica se foi vendido", example = "true")
    val sold: Boolean? = null
){
    fun toUpdate(vehicle: Vehicle): Vehicle {
        return vehicle.copy(
            vehicle = this.vehicle?.trim() ?: vehicle.vehicle,
            brand = this.brand?.trim() ?: vehicle.brand,
            year = this.year ?: vehicle.year,
            description = this.description?.trim() ?: vehicle.description,
            sold = this.sold ?: vehicle.sold
        )
    }
}
