package com.example.orderservice.domain.repository

import com.example.orderservice.domain.entity.order.OrderInfo
import org.springframework.data.jpa.repository.JpaRepository

interface OrdersRepository : JpaRepository<OrderInfo, Long> {
}