package com.example.eventserver.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EventHealthCheckController {

    @GetMapping("/health")
    fun healthCheck(): String {
        return "User - Healthy"
    }
}