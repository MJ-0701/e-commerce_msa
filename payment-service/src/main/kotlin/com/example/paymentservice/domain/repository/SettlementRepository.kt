package com.example.paymentservice.domain.repository

import com.example.paymentservice.domain.entity.Settlement
import org.springframework.data.jpa.repository.JpaRepository

interface SettlementRepository : JpaRepository<Settlement, Long> {
}