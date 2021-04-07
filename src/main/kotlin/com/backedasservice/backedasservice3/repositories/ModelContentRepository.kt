package com.backedasservice.backedasservice3.repositories

import com.backedasservice.backedasservice3.models.ModelContent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ModelContentRepository: JpaRepository<ModelContent, Long> {
    fun findByModelStructureModelName(modelName: String): List<ModelContent>
}