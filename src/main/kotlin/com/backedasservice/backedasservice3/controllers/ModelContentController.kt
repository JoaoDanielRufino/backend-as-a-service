package com.backedasservice.backedasservice3.controllers

import com.backedasservice.backedasservice3.models.ModelContent
import com.backedasservice.backedasservice3.repositories.ModelContentRepository
import com.backedasservice.backedasservice3.repositories.ModelStructureRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@RestController
@RequestMapping("/model-content")
class ModelContentController(val modelContentRepository: ModelContentRepository, val modelStructureRepository: ModelStructureRepository) {
    @GetMapping("/{modelName}")
    fun index(@PathVariable modelName: String): ResponseEntity<Iterable<HashMap<String, Any>>> {
        val modelContents = modelContentRepository.findByModelStructureModelName(modelName)

        modelContents.forEach{x -> x.content.putIfAbsent("id", x.id)}
        val response = modelContents.map { x -> x.content }

        return ResponseEntity.ok(response)
    }

    @GetMapping("/{modelName}/{id}")
    fun show(@PathVariable id: UUID): ResponseEntity<Optional<ModelContent>> {
        val modelContent = modelContentRepository.findById(id)
        return ResponseEntity.ok(modelContent)
    }

    @PostMapping("/{modelName}")
    fun create(@Validated @RequestBody request: HashMap<String, Any>, @PathVariable modelName: String): ResponseEntity<ModelContent> {
        val modelStructure = modelStructureRepository.findByModelName(modelName)!!

        for ((key, value) in request) {
            if (!modelStructure.structure.containsKey(key)) {
                throw Exception("$key does not exists in model structure")
            } else {
                val modelStructureType = modelStructure.structure[key]
                if (value::class.simpleName != modelStructureType) {
                    throw Exception("type of $key(${value::class.simpleName}) does not match $modelStructureType")
                }
            }
        }

        val modelContent = ModelContent(modelStructure = modelStructure, content = request)
        val res = modelContentRepository.save(modelContent)
        return ResponseEntity(res, HttpStatus.CREATED)
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