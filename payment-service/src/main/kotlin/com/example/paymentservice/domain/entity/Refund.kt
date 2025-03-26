package com.example.paymentservice.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "refund")
class Refund(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    var payment: Payment,

    @Column(name = "refund_amount", nullable = false)
    var refundAmount: BigDecimal,

    @Column(name = "refund_method", nullable = false, length = 50)
    var refundMethod: String,

    @Column(name = "refund_status", nullable = false, length = 50)
    var refundStatus: String,

    @Column(name = "refund_details", columnDefinition = "TEXT")
    var refundDetails: String? = null,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)