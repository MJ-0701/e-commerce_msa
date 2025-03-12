package com.example.orderservice.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CouponConsumer {

    @KafkaListener(topics = ["coupon-topic"], groupId = "coupon-consumer-group")
    fun consumeCoupon(couponId: String) {
        // 쿠폰 사용 로직 구현 (선착순으로 처리)
        println("Consumed coupon: $couponId")
        // todo:: 주문에 쿠폰 할당 후, 해당 쿠폰은 더 이상 사용할 수 없도록 처리
    }
}