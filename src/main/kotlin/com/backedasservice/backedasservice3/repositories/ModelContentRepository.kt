package com.backedasservice.backedasservice3.repositories

import com.backedasservice.backedasservice3.models.ModelContent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ModelContentRepository: JpaRepository<ModelContent, UUID> {
    fun findByModelStructureModelName(modelName: String): List<ModelContent>
}