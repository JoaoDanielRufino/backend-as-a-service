package com.backedasservice.backedasservice3.models

import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.util.*
import javax.persistence.*
import kotlin.collections.HashMap

@Entity
@Table(name = "model_content")
@TypeDef(name = "jsonb", typeClass = JsonStringType::class)
data class ModelContent(
    @Id
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    val modelStructure: ModelStructure,

    @Type(type = "jsonb")
    @Column(name = "content", nullable = false, columnDefinition = "text")
    val content: HashMap<String, Any>
)
