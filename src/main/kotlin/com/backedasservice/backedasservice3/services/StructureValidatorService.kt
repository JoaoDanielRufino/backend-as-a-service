package com.backedasservice.backedasservice3.services

import com.backedasservice.backedasservice3.models.ModelStructure
import org.springframework.stereotype.Service

@Service
class StructureValidatorService {
    @Throws(Exception::class)
    fun execute(modelStructure: ModelStructure, request: HashMap<String, Any>) {
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
    }
}