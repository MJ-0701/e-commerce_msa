package com.example.paymentservice.domain.repository

import com.example.paymentservice.domain.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<Payment, Long> {
}