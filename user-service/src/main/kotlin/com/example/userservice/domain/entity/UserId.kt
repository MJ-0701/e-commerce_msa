package com.example.userservice.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Embeddable
class UserId(
    @Column(name = "user_id")
    var userId: String,
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
        private const val PRE_FIX = "UID"

        fun generate(dateTIme : LocalDateTime) : UserId {
            val uuid = UUID.randomUUID().toString().replace("-", "").uppercase(Locale.getDefault())
            return UserId("$PRE_FIX${yyyyMMdd(dateTIme)}$uuid")

        }

        private fun yyyyMMdd(dateTIme : LocalDateTime) = dateTIme.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }
}