package com.example.orderservice.domain.entity

import jakarta.persistence.Column
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class OrderId(
    @Column(name = "order_id")
    val orderId: String,
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
        private const val PRE_FIX = "OID"

        fun generate(dateTIme : LocalDateTime) : OrderId {
            val uuid = UUID.randomUUID().toString().replace("-", "").uppercase(Locale.getDefault())
            return OrderId("$PRE_FIX${yyyyMMdd(dateTIme)}$uuid")

        }

        private fun yyyyMMdd(dateTIme : LocalDateTime) = dateTIme.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    override fun toString(): String = orderId
}