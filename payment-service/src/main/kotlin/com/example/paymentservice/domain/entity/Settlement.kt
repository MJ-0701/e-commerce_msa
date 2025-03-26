package com.example.paymentservice.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "settlement")
class Settlement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    var payment: Payment,

    @Column(name = "store_id", nullable = false)
    var storeId: Long,

    @Column(name = "product_id")
    var productId: Long? = null,

    @Column(name = "settlement_date", nullable = false)
    var settlementDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "settled_amount", nullable = false)
    var settledAmount: BigDecimal,

    @Column(name = "commission_fee", nullable = false)
    var commissionFee: BigDecimal = BigDecimal.ZERO,

    @Column(name = "net_amount", nullable = false)
    var netAmount: BigDecimal,

    @Column(name = "status", nullable = false, length = 50)
    var status: String,

    @Column(name = "details", columnDefinition = "TEXT")
    var details: String? = null,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
)