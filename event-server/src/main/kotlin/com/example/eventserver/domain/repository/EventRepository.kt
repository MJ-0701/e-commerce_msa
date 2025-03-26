package com.example.eventserver.domain.repository

import com.example.eventserver.domain.entity.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, Long> {
}