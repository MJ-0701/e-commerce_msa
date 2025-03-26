package com.example.productservice.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "store")
class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    val id: Long? = null,

    @Column(name = "store_name", nullable = false)
    var storeName: String,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "contact_email")
    var contactEmail: String? = null,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "address")
    var address: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)