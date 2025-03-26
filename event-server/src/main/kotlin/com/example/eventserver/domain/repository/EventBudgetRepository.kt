package com.example.eventserver.domain.repository

import com.example.eventserver.domain.entity.EventBudget
import org.springframework.data.jpa.repository.JpaRepository

interface EventBudgetRepository : JpaRepository<EventBudget, Long> {
}