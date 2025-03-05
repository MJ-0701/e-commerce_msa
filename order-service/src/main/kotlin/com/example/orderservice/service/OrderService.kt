package com.example.orderservice.service

import org.springframework.stereotype.Service

@Service
class OrderService (
    private val orderEventProducer: OrderEventProducer
) {

    fun postOrderService() {

        orderEventProducer.sendOrderCompletedEvent("메세지 큐 확인")
    }

}