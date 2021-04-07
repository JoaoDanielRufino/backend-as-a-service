package com.backedasservice.backedasservice3.models

import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.*
import javax.persistence.*
import kotlin.collections.HashMap

@Entity
@Table(name = "model_structure")
@TypeDef(name = "jsonb", typeClass = JsonStringType::class)
data class ModelStructure(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "model_name", nullable = false)
    val modelName: String,

    @Type(type = "jsonb")
    @Column(name = "structure", nullable = false, columnDefinition = "text")
    val structure: HashMap<String, String>
)
