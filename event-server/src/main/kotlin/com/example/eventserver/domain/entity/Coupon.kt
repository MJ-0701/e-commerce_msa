package com.example.eventserver.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "coupon")
class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_budget_id", nullable = false)
    var eventBudget: EventBudget,

    @Column(name = "coupon_code", nullable = false, length = 50, unique = true)
    var couponCode: String,

    @Column(name = "issued_at", nullable = false)
    var issuedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "redeemed_at")
    var redeemedAt: LocalDateTime? = null,

    @Column(name = "expiration_date", nullable = false)
    var expirationDate: LocalDateTime
)
