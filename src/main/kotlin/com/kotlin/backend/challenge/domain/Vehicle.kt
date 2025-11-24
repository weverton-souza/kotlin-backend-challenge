package com.kotlin.backend.challenge.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "vehicles")
data class Vehicle(
    @Id
    @field:Column
    override var id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val vehicle: String,

    @Column(nullable = false, length = 100)
    val brand: String,

    @Column(nullable = false)
    val year: Int,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(nullable = false)
    val sold: Boolean = false,

    @Column(length = 50)
    val color: String,
): AuditoryEntity(id = id)
