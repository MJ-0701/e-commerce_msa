package com.example.paymentservice.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment_failure")
class PaymentFailure(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "failure_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    var payment: Payment,

    @Column(name = "failure_reason", nullable = false, columnDefinition = "TEXT")
    var failureReason: String,

    @Column(name = "failure_code", length = 50)
    var failureCode: String? = null,

    @Column(name = "additional_details", columnDefinition = "TEXT")
    var additionalDetails: String? = null,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
)