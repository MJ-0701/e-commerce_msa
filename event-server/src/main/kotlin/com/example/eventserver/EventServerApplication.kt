package com.example.eventserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EventServerApplication

fun main(args: Array<String>) {
	runApplication<EventServerApplication>(*args)
}
