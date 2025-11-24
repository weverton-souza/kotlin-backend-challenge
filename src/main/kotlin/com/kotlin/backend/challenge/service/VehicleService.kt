package com.kotlin.backend.challenge.service

import com.kotlin.backend.challenge.payload.request.VehicleCreateRequest
import com.kotlin.backend.challenge.payload.request.VehicleUpdateRequest
import com.kotlin.backend.challenge.payload.response.VehicleResponse
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface VehicleService {
    fun create(request: VehicleCreateRequest): VehicleResponse
    fun update(id: UUID, request: VehicleUpdateRequest): VehicleResponse
    fun partialUpdate(id: UUID, request: VehicleUpdateRequest): VehicleResponse
    fun findById(id: UUID): VehicleResponse
    fun findAll(pageable: Pageable, brand: String?, year: Int?, color: String?): Page<VehicleResponse>
    fun delete(id: UUID)
}
