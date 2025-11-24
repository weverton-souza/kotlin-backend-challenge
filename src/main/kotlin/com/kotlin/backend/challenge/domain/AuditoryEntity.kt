package com.kotlin.backend.challenge.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@SQLRestriction("deleted=false")
@EntityListeners(AuditingEntityListener::class)
class AuditoryEntity(
    @Id
    @field:Column(name = "id")
    var id: UUID,

    @field:Column(name = "deleted")
    var deleted: Boolean = false,

    @CreatedDate
    @field:Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @field:Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
)
