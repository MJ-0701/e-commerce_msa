package com.example.productservice.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductHealthCheckController {

    @GetMapping("/health")
    fun healthCheck(): String {
        return "Product - Healthy"
    }
}