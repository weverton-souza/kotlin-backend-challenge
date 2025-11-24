package com.kotlin.backend.challenge.payload.request

import com.kotlin.backend.challenge.domain.Vehicle
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Schema(description = "Dados para criação/atualização de um veículo")
data class VehicleCreateRequest(
    @field:NotBlank(message = "O nome do veículo é obrigatório")
    @field:Size(max = 255, message = "O nome do veículo deve ter no máximo 255 caracteres")
    @Schema(description = "Nome/modelo do veículo", example = "Civic")
    val vehicle: String,

    @field:NotBlank(message = "A marca é obrigatória")
    @field:Size(max = 100, message = "A marca deve ter no máximo 100 caracteres")
    @Schema(description = "Marca/fabricante", example = "Honda")
    val brand: String,

    @field:NotNull(message = "O ano é obrigatório")
    @field:Min(value = 1886, message = "O ano deve ser maior ou igual a 1886")
    @field:Max(value = 2026, message = "O ano não pode ser maior que o próximo ano")
    @Schema(description = "Ano de fabricação", example = "2023")
    val year: Int,

    @Schema(description = "Descrição do veículo", example = "Sedan executivo com motor 2.0")
    val description: String? = null,

    @field:NotNull(message = "O status de vendido é obrigatório")
    @Schema(description = "Indica se foi vendido", example = "false")
    val sold: Boolean = false,

    @Schema(description = "Cor do veículo", example = "Vermelho")
    val color: String,
) {
    fun toEntity(): Vehicle = Vehicle(
        vehicle = vehicle.trim(),
        brand = brand.trim(),
        year = year,
        description = description?.trim(),
        color = color.trim(),
        sold = sold
    )
}
