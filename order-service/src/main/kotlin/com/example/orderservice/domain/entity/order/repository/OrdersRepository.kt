package com.example.orderservice.domain.entity.order.repository

import com.example.orderservice.domain.entity.order.Orders
import org.springframework.data.jpa.repository.JpaRepository

interface OrdersRepository : JpaRepository<Orders, Long> {
}