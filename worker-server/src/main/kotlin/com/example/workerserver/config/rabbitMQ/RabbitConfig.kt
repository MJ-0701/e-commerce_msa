package com.example.workerserver.config.rabbitMQ

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    companion object {
        const val ORDER_EXCHANGE = "order.exchange"
        const val ORDER_COMPLETED_QUEUE = "order.completed.queue"
        const val ORDER_COMPLETED_ROUTING_KEY = "order.completed"
    }

    @Bean
    fun orderExchange(): TopicExchange = TopicExchange(ORDER_EXCHANGE)

    @Bean
    fun orderCompletedQueue(): Queue = Queue(ORDER_COMPLETED_QUEUE)

    @Bean
    fun orderCompletedBinding(orderCompletedQueue: Queue, orderExchange: TopicExchange): Binding {
        return BindingBuilder.bind(orderCompletedQueue)
            .to(orderExchange)
            .with(ORDER_COMPLETED_ROUTING_KEY)
    }
}