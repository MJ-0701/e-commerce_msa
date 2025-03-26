package com.example.eventserver.domain.repository

import com.example.eventserver.domain.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long> {
}