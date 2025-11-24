package com.kotlin.backend.challenge.service.impl

import com.kotlin.backend.challenge.configuration.annotation.Loggable
import com.kotlin.backend.challenge.exception.ResourceNotFoundException
import com.kotlin.backend.challenge.payload.request.VehicleCreateRequest
import com.kotlin.backend.challenge.payload.request.VehicleUpdateRequest
import com.kotlin.backend.challenge.payload.response.VehicleResponse
import com.kotlin.backend.challenge.reporsitory.VehicleRepository
import com.kotlin.backend.challenge.reporsitory.extensions.existsByIdOrThrow
import com.kotlin.backend.challenge.reporsitory.extensions.findByIdOrThrow
import com.kotlin.backend.challenge.service.VehicleService
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Loggable
@Transactional
class VehicleServiceImpl(
    private val repository: VehicleRepository
) : VehicleService {
    override fun create(request: VehicleCreateRequest): VehicleResponse {
        val vehicle = request.toEntity()
        val saved = repository.save(vehicle)
        return VehicleResponse.from(saved)
    }

    override fun update(id: UUID, request: VehicleUpdateRequest): VehicleResponse {
        val vehicle = repository.findByIdOrThrow(id)

        val toUpdate = request.toUpdate(vehicle).copy(id = id)
        val updated = repository.save(toUpdate)
        return VehicleResponse.from(updated)
    }

    override fun partialUpdate(id: UUID, request: VehicleUpdateRequest): VehicleResponse {
        val existing = repository.findByIdOrThrow(id)

        val updated = existing.copy(
            vehicle = request.vehicle?.trim() ?: existing.vehicle,
            brand = request.brand?.trim() ?: existing.brand,
            year = request.year ?: existing.year,
            description = request.description?.trim() ?: existing.description,
            sold = request.sold ?: existing.sold
        )

        val saved = repository.save(updated)
        return VehicleResponse.from(saved)
    }

    override fun findById(id: UUID): VehicleResponse {
        val vehicle = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Veículo com ID $id não encontrado") }
        return VehicleResponse.from(vehicle)
    }

    override fun findAll(pageable: Pageable, brand: String?, year: Int?, color: String?): Page<VehicleResponse> {
        return repository.findByFilters(pageable, brand, year, color)
            .map { VehicleResponse.from(it) }
    }

    override fun delete(id: UUID) {
        repository.existsByIdOrThrow(id)
        repository.deleteById(id)
    }
}