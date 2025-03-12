package com.example.eventserver.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class CouponProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    private val couponTopic = "coupon-topic" // 쿠폰 토픽 이름

    fun publishCoupons() {
        // 예를 들어, 100개의 쿠폰을 발행
        for (i in 1..100) {
            val couponId = "COUPON-${"%03d".format(i)}"
            kafkaTemplate.send(couponTopic, couponId)
            println("Published coupon: $couponId")
        }
    }
}