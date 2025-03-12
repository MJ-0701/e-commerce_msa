package com.example.orderservice.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OrderService (
    private val orderEventProducer: OrderEventProducer,
    private val couponConsumer: CouponConsumer
) {


    fun postOrderService() {

        orderEventProducer.sendOrderCompletedEvent("메세지 큐 확인")

        couponConsumer.consumeCoupon("선착순 100명 할인쿠폰 동작 확인")
    }

}