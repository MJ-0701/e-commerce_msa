package com.example.orderservice.service

import com.example.orderservice.domain.entity.OrderInfo
import com.example.orderservice.domain.entity.OrderItem
import com.example.orderservice.domain.repository.OrderInfoRepository
import com.example.orderservice.domain.repository.OrderItemRepository
import com.example.orderservice.web.dto.OrderDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService (
    private val orderEventProducer: OrderEventProducer,
    private val couponConsumer: CouponConsumer,
    private val orderInfoRepository : OrderInfoRepository,
    private val orderItemRepository: OrderItemRepository,
) {


    @Transactional
    fun postOrderService(
        orderItem : OrderDto.PostOrderItemRequestDto
    ) {

        val orderInfo = OrderInfo()

        val orderItemEntity = OrderItem(
            productId = orderItem.productId,
            productName = orderItem.productName,
            price = orderItem.price,
            quantity = orderItem.quantity,
            couponId = orderItem.couponId,
        )

        // 주문에 주문 항목 추가 (양방향 연관관계 설정)
        orderInfo.addItem(orderItemEntity)

        // 주문 저장 (cascade 옵션으로 OrderItem도 저장됨)
        orderInfoRepository.save(orderInfo)


        orderEventProducer.sendOrderCompletedEvent("메세지 큐 확인")

        couponConsumer.consumeCoupon("선착순 100명 할인쿠폰 동작 확인")
    }

}