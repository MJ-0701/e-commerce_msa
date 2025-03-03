package com.example.orderservice.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "order_item")
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // product-service에 있는 상품의 ID (양 서비스에서 동일한 형식 사용)
    @Column(name = "product_id", nullable = false)
    val productId: Long,

    // 주문 당시의 상품 정보 스냅샷 (예: 상품명, 가격 등)
    @Column(name = "product_name", nullable = false)
    val productName: String,

    @Column(name = "price", nullable = false)
    val price: Double,

    @Column(name = "quantity", nullable = false)
    val quantity: Int
) {
    // OrderInfo 엔티티와의 연관관계 (다대일 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_info_id", nullable = false, insertable = false, updatable = false)
    lateinit var orderInfo: OrderInfo
}