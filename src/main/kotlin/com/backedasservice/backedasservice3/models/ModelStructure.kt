package com.backedasservice.backedasservice3.models

import com.fasterxml.jackson.databind.JsonNode
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table(name = "model_structure")
@TypeDef(name = "jsonb", typeClass = JsonStringType::class)
data class ModelStructure(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(name = "model_name", nullable = false)
    val modelName: String,

//    @Column(name = "structure", nullable = false)
//    val structure: HashMap<String, String>,

    @Type(type = "jsonb")
    @Column(name = "structure", nullable = false, columnDefinition = "text")
    val structure: HashMap<String, String>,

//    @OneToMany(mappedBy = "modelStructure")
//    val modelContent: Set<ModelContent>?
)
