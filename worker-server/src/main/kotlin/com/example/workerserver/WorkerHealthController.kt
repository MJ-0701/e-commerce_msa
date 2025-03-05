package com.example.workerserver

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WorkerHealthController {

    @GetMapping("/health")
    fun health() = "Worker - Health"
}