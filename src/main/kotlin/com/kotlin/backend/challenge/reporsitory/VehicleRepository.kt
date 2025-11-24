package com.kotlin.backend.challenge.reporsitory

import com.kotlin.backend.challenge.domain.Vehicle
import java.util.UUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository : JpaRepository<Vehicle, UUID> {
    @Query(
        value = """
        SELECT * FROM vehicles 
        WHERE (:brand IS NULL OR LOWER(brand) = LOWER(CAST(:brand AS VARCHAR)))
          AND (:year IS NULL OR year = :year)
          AND (:color IS NULL OR LOWER(color) = LOWER(CAST(:color AS VARCHAR)))
    """, nativeQuery = true
    )
    fun findByFilters(
        pageable: Pageable,
        @Param("brand") brand: String?,
        @Param("year") year: Int?,
        @Param("color") color: String?
    ): Page<Vehicle>
}
