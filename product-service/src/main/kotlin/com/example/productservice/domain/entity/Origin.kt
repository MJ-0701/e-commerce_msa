package com.example.productservice.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "origin")
class Origin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "origin_id")
    val id: Long? = null,

    @Column(name = "country", nullable = false)
    val country: String,

    @Column(name = "region")
    val region: String? = null,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)