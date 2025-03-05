package com.example.orderservice.web.controller

import com.example.orderservice.service.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderRestController(
    private val orderService: OrderService
) {

    @PostMapping("/complete")
    fun postOrder() : String {

        orderService.postOrderService()

        return "OK"
    }
}