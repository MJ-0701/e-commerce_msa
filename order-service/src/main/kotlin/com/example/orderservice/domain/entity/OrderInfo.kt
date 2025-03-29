package com.example.orderservice.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "order_info")
class OrderInfo(
    @Column(name = "order_date",nullable = false)
    val orderDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "order_number", nullable = false, unique = true)
    var orderNumber : String = OrderId.generate(LocalDateTime.now()).orderId,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_info_id")
    val id: Long = 0



    // 추가 주문 관련 정보(예: 주문 상태, 고객 정보 등)을 필요에 따라 추가
    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var orderItem: MutableList<OrderItem> = mutableListOf()


    // 주문 상품을 편리하게 추가할 수 있는 메서드
    fun addItem(item: OrderItem) {
        orderItem.add(item)
        item.orderInfo = this
    }

    @PrePersist
    fun prePersist() {
        if (orderNumber.isBlank()) {
            orderNumber = OrderId.generate(LocalDateTime.now()).orderId
            println("orderNumber set to: $orderNumber")
        }
    }
}