package com.example.orderservice.web.dto

object OrderDto {

    data class PostOrderItemRequestDto(
        val productId : Long,
        val productName : String,
        val price : Int,
        val quantity : Int,
        val couponId : Long,
    )

}