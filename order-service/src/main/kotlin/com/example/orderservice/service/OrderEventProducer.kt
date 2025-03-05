package com.example.orderservice.service

import com.example.orderservice.config.RabbitConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class OrderEventProducer(
    private val rabbitTemplate: RabbitTemplate,
) {
    fun sendOrderCompletedEvent(orderId: String) {
        rabbitTemplate.convertAndSend(
            RabbitConfig.ORDER_EXCHANGE,
            RabbitConfig.ORDER_COMPLETED_ROUTING_KEY,
            orderId
        )
        println("Sent order completed event for order: $orderId")
    }
}