package com.example.orderservice.service

import com.example.orderservice.domain.entity.OrderInfo
import com.example.orderservice.domain.repository.OrderInfoRepository
import com.example.orderservice.web.dto.OrderDto
import com.netflix.discovery.EurekaClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(properties = [
 "eureka.client.enabled=false",
 "eureka.client.register-with-eureka=false",
 "eureka.client.fetch-registry=false",
])
@ActiveProfiles("default")
@Transactional
class OrderServiceTest @Autowired constructor(
 private val orderService: OrderService,
 private val orderInfoRepository: OrderInfoRepository
) {


 @Test
 fun `주문 생성 시 OrderInfo와 OrderItem이 정상적으로 저장되어야 한다`() {
  // given: 주문 생성에 필요한 DTO 준비
  val orderItemDto = OrderDto.PostOrderItemRequestDto(
   productId = 100L,
   productName = "테스트 상품",
   price = 50,
   quantity = 2,
   couponId = 10L  // 필요하다면 nullable로 처리하거나 적절한 값 설정
  )

  // when: 주문 생성 메서드 호출
  orderService.postOrderService(orderItemDto)

  // then: 주문 정보(OrderInfo)와 연관된 주문 항목(OrderItem)이 저장되어 있는지 확인
  val orderInfos: List<OrderInfo> = orderInfoRepository.findAll()
  assertThat(orderInfos).isNotEmpty

  val savedOrder = orderInfos.first()
  // 주문 번호가 생성되어 있는지, 주문 날짜가 설정되어 있는지 등 추가 검증 가능
//  assertThat(savedOrder.orderNumber).isNotBlank()
  // 주문 항목(orderItem) 리스트가 비어있지 않은지 확인
  assertThat(savedOrder.orderItem).isNotEmpty
  // 첫 번째 주문 항목의 couponId가 DTO와 일치하는지 확인
  assertThat(savedOrder.orderItem.first().couponId).isEqualTo(orderItemDto.couponId)
 }
}