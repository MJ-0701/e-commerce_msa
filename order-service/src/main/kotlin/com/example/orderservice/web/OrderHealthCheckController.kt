package com.example.orderservice.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderHealthCheckController {

    @GetMapping("/health")
    fun healthCheck(): String {
        return "Order - Healthy"
    }
}