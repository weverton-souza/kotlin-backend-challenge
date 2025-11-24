package com.kotlin.backend.challenge.reporsitory.extensions

import com.kotlin.backend.challenge.domain.Vehicle
import com.kotlin.backend.challenge.exception.ResourceNotFoundException
import com.kotlin.backend.challenge.extensions.OptionalExtensions.orThrowNotFound
import com.kotlin.backend.challenge.reporsitory.VehicleRepository
import java.util.UUID

fun VehicleRepository.findByIdOrThrow(id: UUID): Vehicle = findById(id).orThrowNotFound()

fun VehicleRepository.findAllByIdOrThrow(ids: MutableSet<UUID>): MutableSet<Vehicle> = findAllById(ids).toMutableSet()

fun VehicleRepository.existsByIdOrThrow(id: UUID) {
    if (!existsById(id)) {
        throw ResourceNotFoundException("Vehicle with id $id not found")
    }
}