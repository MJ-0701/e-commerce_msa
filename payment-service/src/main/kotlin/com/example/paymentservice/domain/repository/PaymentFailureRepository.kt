package com.example.paymentservice.domain.repository

import com.example.paymentservice.domain.entity.PaymentFailure
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentFailureRepository : JpaRepository<PaymentFailure, Long> {
}