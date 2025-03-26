package com.example.paymentservice.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payment")
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    val id: Long? = null,

    @Column(name = "order_info_id", nullable = false)
    val orderInfoId: Long,

    @Column(name = "payment_type", nullable = false, length = 50)
    var paymentType: String,

    @Column(name = "payment_method", nullable = false, length = 50)
    var paymentMethod: String,

    @Column(name = "payment_details", columnDefinition = "TEXT")
    var paymentDetails: String? = null,

    @Column(name = "amount", nullable = false)
    var amount: BigDecimal,

    @Column(name = "status", nullable = false, length = 50)
    var status: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)