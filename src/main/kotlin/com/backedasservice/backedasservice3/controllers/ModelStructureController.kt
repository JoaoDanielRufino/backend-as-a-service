package com.backedasservice.backedasservice3.controllers

import com.backedasservice.backedasservice3.models.ModelStructure
import com.backedasservice.backedasservice3.repositories.ModelStructureRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/model-structure")
class ModelStructureController(val modelStructureRepository: ModelStructureRepository) {
    @GetMapping
    fun index(): ResponseEntity<Iterable<ModelStructure>> {
        val modelStructures = modelStructureRepository.findAll()
        return ResponseEntity.ok(modelStructures)
    }

    @PostMapping
    fun create(@Validated @RequestBody request: ModelStructure): ResponseEntity<ModelStructure> {
//        val modelStructure = ModelStructure(modelName = request.modelName, structure = request.structure)
//        val res = modelStructureRepository.save(modelStructure)
        val res = modelStructureRepository.save(request)
        return ResponseEntity(res, HttpStatus.CREATED)
    }
}

class ModelStructureRequest(
    val modelName: String,
    val structure: HashMap<String, String>
)