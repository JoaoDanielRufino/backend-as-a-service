package com.backedasservice.backedasservice3.models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table(name = "model_content")
@TypeDef(name = "jsonb", typeClass = JsonStringType::class)
data class ModelContent(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id", nullable = true, insertable = false, updatable = false)
    val modelStructure: ModelStructure,

    @Type(type = "jsonb")
    @Column(name = "content", nullable = false, columnDefinition = "text")
    val content: HashMap<String, Any>
)
