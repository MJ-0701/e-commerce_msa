package com.example.workerserver.listener

import com.example.workerserver.config.rabbitMQ.RabbitConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class OrderEventListener {

    @RabbitListener(queues = [RabbitConfig.ORDER_COMPLETED_QUEUE])
    fun handleOrderCompleted(orderId: String) {
        println("Received order completed event for order: $orderId")
//      todo :: 실제 알림 전송, 후처리 등의 로직 구현
    }
}