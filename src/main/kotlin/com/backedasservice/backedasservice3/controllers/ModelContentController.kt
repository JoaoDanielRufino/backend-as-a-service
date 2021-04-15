package com.backedasservice.backedasservice3.controllers

import com.backedasservice.backedasservice3.models.ModelContent
import com.backedasservice.backedasservice3.repositories.ModelContentRepository
import com.backedasservice.backedasservice3.repositories.ModelStructureRepository
import com.backedasservice.backedasservice3.services.StructureValidatorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@RestController
@RequestMapping("/model-content")
class ModelContentController(
    val modelContentRepository: ModelContentRepository,
    val modelStructureRepository: ModelStructureRepository,
    val structureValidatorService: StructureValidatorService
    ) {
    @GetMapping("/{modelName}")
    fun index(@PathVariable modelName: String): ResponseEntity<Iterable<HashMap<String, Any>>> {
        val modelStructure = modelStructureRepository.findByModelName(modelName)

        if (modelStructure != null) {
            val modelContents = modelContentRepository.findByModelStructureModelName(modelName)

            modelContents.forEach{x -> x.content.putIfAbsent("id", x.id)}
            val response = modelContents.map { x -> x.content }

            return ResponseEntity.ok(response)
        }

        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/{modelName}/{id}")
    fun show(@PathVariable id: UUID): ResponseEntity<HashMap<String, Any>> {
        val modelContent = modelContentRepository.findById(id)
        if (modelContent.isPresent) {
            modelContent.get().content.putIfAbsent("id", id)
            return ResponseEntity.ok(modelContent.get().content)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/{modelName}")
    fun create(@Validated @RequestBody request: HashMap<String, Any>, @PathVariable modelName: String): ResponseEntity<ModelContent> {
        val modelStructure = modelStructureRepository.findByModelName(modelName)

        if (modelStructure != null) {
            return try {
                structureValidatorService.execute(modelStructure, request)
                val modelContent = ModelContent(modelStructure = modelStructure, content = request)
                val res = modelContentRepository.save(modelContent)
                ResponseEntity(res, HttpStatus.CREATED)
            } catch (e: Exception) {
                println(e.message)
                ResponseEntity(HttpStatus.BAD_REQUEST)
            }
        }

        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{modelName}/{id}")
    fun update(@Validated @RequestBody request: HashMap<String, Any>, @PathVariable id: UUID): ResponseEntity<ModelContent> {
        val modelContent = modelContentRepository.findById(id)
        if (modelContent.isPresent) {
            modelContent.get().content = request
            modelContentRepository.save(modelContent.get())
            return ResponseEntity.ok(modelContent.get())
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/{modelName}/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<String> {
        val modelContent = modelContentRepository.findById(id)
        if (modelContent.isPresent) {
            modelContentRepository.deleteById(id)
            return ResponseEntity(HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}