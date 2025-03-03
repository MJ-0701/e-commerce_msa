package com.example.orderservice.domain.repository

import com.example.orderservice.domain.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemRepository : JpaRepository<OrderItem, Long> {
}