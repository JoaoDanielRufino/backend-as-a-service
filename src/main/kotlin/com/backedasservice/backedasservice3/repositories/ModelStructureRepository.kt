package com.backedasservice.backedasservice3.repositories

import com.backedasservice.backedasservice3.models.ModelStructure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ModelStructureRepository: JpaRepository<ModelStructure, Long> {
    fun findByModelName(modelName: String): ModelStructure?
}