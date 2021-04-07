package com.backedasservice.backedasservice3.controllers.requests

data class ModelStructurePostRequest(
    val modelName: String,
    val structure: HashMap<String, String>
)
