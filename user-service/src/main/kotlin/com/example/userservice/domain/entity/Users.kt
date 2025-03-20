package com.example.userservice.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "users")
@Entity
class Users(
    userName : String,
    email : String,
    passwordHash : String,
    emailVerified : Boolean,
    phone : String,
    ci : String,
    isActive: Boolean,
    lastLoginAt: LocalDateTime,
) : BaseTimeEntity() {

    @EmbeddedId
    val id: UserId = UserId.generate(LocalDateTime.now())

    @Column(name = "username")
    var userName : String = userName
        protected set

    @Column(name = "email")
    var email : String = email
        protected set

    @Column(name = "password_hash")
    var passwordHash : String = passwordHash
        protected set

    @Column(name = "email_verified")
    var emailVerified : Boolean = emailVerified
        protected set

    @Column(name = "phone")
    var phone : String = phone
        protected set

    @Column(name = "ci")
    var ci : String = ci
        protected set

    @Column(name = "is_active")
    var isActive : Boolean = isActive
        protected set

    @Column(name = "last_login_at")
    var lastLoginAt: LocalDateTime = lastLoginAt
        protected set

}