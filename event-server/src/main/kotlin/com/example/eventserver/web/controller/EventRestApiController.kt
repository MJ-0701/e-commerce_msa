package com.example.eventserver.web.controller

import com.example.eventserver.service.CouponProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EventRestApiController(
    private val couponProducer: CouponProducer,
) {

    @PostMapping("/coupon/producer")
    fun producerCoupon() = couponProducer.publishCoupons()

}