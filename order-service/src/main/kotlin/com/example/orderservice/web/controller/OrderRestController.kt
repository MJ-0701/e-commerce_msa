package com.example.orderservice.web.controller

import com.example.common.http.ResponseObject
import com.example.orderservice.service.OrderService
import com.example.orderservice.web.dto.OrderDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderRestController(
    private val orderService: OrderService
) {

    @PostMapping("/complete")
    fun postOrder(
        @RequestBody orderItem : OrderDto.PostOrderItemRequestDto
    ) : ResponseEntity<ResponseObject<String>> {

        orderService.postOrderService(orderItem)

        return ResponseEntity.ok().body(ResponseObject.of("OK"))
    }
}