package com.example.paymentservice.domain.repository

import com.example.paymentservice.domain.entity.Refund
import org.springframework.data.jpa.repository.JpaRepository

interface RefundRepository : JpaRepository<Refund, Long> {
}