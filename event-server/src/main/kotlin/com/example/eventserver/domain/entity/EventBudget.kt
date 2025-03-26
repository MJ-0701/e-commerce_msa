package com.example.eventserver.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "event_budget")
class EventBudget(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_budget_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    var event: Event,

    @Column(name = "discount_type", nullable = false, length = 20)
    var discountType: String,

    @Column(name = "coupon_amount", precision = 10, scale = 2)
    var couponAmount: BigDecimal? = null,

    @Column(name = "percentage_discount", precision = 5, scale = 2)
    var percentageDiscount: BigDecimal? = null,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)