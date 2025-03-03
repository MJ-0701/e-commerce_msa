package com.example.orderservice.domain.repository

import com.example.orderservice.domain.entity.OrderInfo
import org.springframework.data.jpa.repository.JpaRepository

interface OrderInfoRepository : JpaRepository<OrderInfo, Long> {
}