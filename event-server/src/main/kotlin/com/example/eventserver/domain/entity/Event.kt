package com.example.eventserver.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "event")
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    val id: Long? = null,

    @Column(name = "event_type", nullable = false, length = 100)
    var eventType: String,

    @Column(name = "aggregate_id", nullable = false)
    var aggregateId: Long,

    @Column(name = "aggregate_type", nullable = false, length = 100)
    var aggregateType: String,

    @Column(name = "payload", columnDefinition = "TEXT")
    var payload: String? = null,

    @Column(name = "status", nullable = false, length = 50)
    var status: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "processed_at")
    var processedAt: LocalDateTime? = null
)