package com.example.userservice.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserHealthCheckController {

    @GetMapping("/health")
    fun healthCheck(): String {
        return "User - Healthy"
    }
}