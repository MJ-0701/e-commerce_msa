package com.example.productservice.domain.repository

import com.example.productservice.domain.entity.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<Store, Long> {
}