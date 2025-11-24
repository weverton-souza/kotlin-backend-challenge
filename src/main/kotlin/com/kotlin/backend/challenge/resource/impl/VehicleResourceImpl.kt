package com.kotlin.backend.challenge.resource.impl

import com.kotlin.backend.challenge.payload.request.VehicleCreateRequest
import com.kotlin.backend.challenge.payload.request.VehicleUpdateRequest
import com.kotlin.backend.challenge.payload.response.VehicleResponse
import com.kotlin.backend.challenge.resource.VehicleResource
import com.kotlin.backend.challenge.service.VehicleService
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/vehicles")
class VehicleResourceImpl(
    private val vehicleService: VehicleService
) : VehicleResource {
    @PostMapping
    override fun create(@RequestBody request: VehicleCreateRequest): ResponseEntity<VehicleResponse> =
        responseEntity(vehicleService.create(request), HttpStatus.CREATED)

    @PutMapping("/{id}")
    override fun update(
        @PathVariable id: UUID,
        @RequestBody request: VehicleUpdateRequest
    ): ResponseEntity<VehicleResponse> =
        responseEntity(vehicleService.update(id, request))

    @PatchMapping("/{id}")
    override fun partialUpdate(
        @PathVariable id: UUID,
        @RequestBody request: VehicleUpdateRequest
    ): ResponseEntity<VehicleResponse> =
        responseEntity(vehicleService.partialUpdate(id, request))

    @GetMapping("/{id}")
    override fun findById(@PathVariable id: UUID): ResponseEntity<VehicleResponse> =
        responseEntity(body = vehicleService.findById(id))

    @GetMapping
    override fun findAll(
        @RequestParam parameters: Map<String, Any>,
        @RequestParam(required = false) brand: String?,
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) color: String?
    ): ResponseEntity<Page<VehicleResponse>> =
        responseEntity(
            body = vehicleService.findAll(
                pageable = this.retrievePageableParameter(parameters = parameters),
                brand = brand,
                year = year,
                color = color
            )
        )

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: UUID): ResponseEntity<Unit> {
        vehicleService.delete(id)
        return ResponseEntity.noContent().build()
    }
}