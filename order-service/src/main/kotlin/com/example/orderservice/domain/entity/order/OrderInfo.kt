package com.example.orderservice.domain.entity.order

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "order_info")
class OrderInfo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val orderNumber: String,

    @Column(nullable = false)
    val orderDate: LocalDateTime = LocalDateTime.now(),

    // 추가 주문 관련 정보(예: 주문 상태, 고객 정보 등)을 필요에 따라 추가
    @OneToMany(mappedBy = "orderInfo", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val items: MutableList<OrderItem> = mutableListOf()

) {
    // 주문 상품을 편리하게 추가할 수 있는 메서드
    fun addItem(item: OrderItem) {
        items.add(item)
        item.orderInfo = this
    }

}