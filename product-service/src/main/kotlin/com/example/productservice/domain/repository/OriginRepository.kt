package com.example.productservice.domain.repository

import com.example.productservice.domain.entity.Origin

import org.springframework.data.jpa.repository.JpaRepository

interface OriginRepository : JpaRepository<Origin, Long> {
}