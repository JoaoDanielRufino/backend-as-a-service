package com.backedasservice.backedasservice3.controllers

import com.backedasservice.backedasservice3.models.ModelContent
import com.backedasservice.backedasservice3.repositories.ModelContentRepository
import com.backedasservice.backedasservice3.repositories.ModelStructureRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/{modelName}")
    fun create(@Validated @RequestBody request: HashMap<String, Any>, @PathVariable modelName: String): ResponseEntity<ModelContent> {
        // 1: find por name em ModelStructre
        // 2: validar a strutura
        // 3: Se OK inserir no modelContent
        val modelStructure = modelStructureRepository.findByModelName(modelName)!!
        val modelContent = ModelContent(modelStructure = modelStructure, content = request)
        val res = modelContentRepository.save(modelContent)
        return ResponseEntity(res, HttpStatus.CREATED)
    }
}