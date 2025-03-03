package com.example.common.config

import com.querydsl.core.types.dsl.PathBuilderFactory
import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQuery
import jakarta.persistence.EntityManager
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.support.PageableExecutionUtils

open class QueryDslPageAndSort(
    private val entityManager: EntityManager,
    private val clazz: Class<*>

) {

    private fun getQuerydsl(): Querydsl {
        val builder = PathBuilderFactory().create(clazz)
        return Querydsl(entityManager, builder)
    }

    private fun getQuerydslWithSort(clazz: Class<*>): Querydsl {
        val builder = PathBuilderFactory().create(clazz)
        return Querydsl(entityManager, builder)
    }

    fun <T> getPageImpl(pageable: Pageable, query: JPQLQuery<T>): PageImpl<T> {
        val totalCount = query.fetchCount()
        val results = getQuerydsl()
            .applyPagination(pageable, query)
            .fetch()

        return PageImpl(results, pageable, totalCount)
    }

    fun <T> getCustomPageImpl(pageable: Pageable, query: JPQLQuery<T>): Page<T> {
        val results = getQuerydsl()
            .applyPagination(pageable, query)
            .fetch()

        return PageableExecutionUtils.getPage(results, pageable, query::fetchCount)
    }

    fun <T> getPageImpl(pageable: Pageable, query: JPQLQuery<T>, count: Long): PageImpl<T> {
        val results = getQuerydsl()
            .applyPagination(pageable, query)
            .fetch()

        return PageImpl(results, pageable, count)
    }

    fun <T> getRandomPageImpl(pageable: Pageable, query: JPQLQuery<T>): PageImpl<T> {
        val totalCount = query.fetchCount()

        val results = getQuerydsl()
            .applyPagination(pageable, query)
            .fetch().shuffled()

        return PageImpl(results, pageable, totalCount)
    }

    // 코틀린 익스텐션으로 JPAQuery 에 페이징 기능 추가
    fun <T> JPAQuery<T>.withPageable(pageable: Pageable): JPAQuery<T> {
        return this.limit(pageable.pageSize.toLong())
            .offset(pageable.offset)

    }

    // 테이블 조인 시, 같은 컬럼명을 가진 컬럼으로 정렬을 해야할 때
    fun <T> getPageImplWithSort(pageable: Pageable, query: JPQLQuery<T>, clazz: Class<*>): PageImpl<T> {
        val totalCount = query.fetchCount()
        val results = getQuerydslWithSort(clazz)
            .applyPagination(pageable, query)
            .fetch()

        return PageImpl(results, pageable, totalCount)
    }

    fun <T> getSlicePageImpl(
        pageable: Pageable,
        query: JPQLQuery<T>
    ): Slice<T> {
        val results = getQuerydsl()
            .applyPagination(pageable, query)
            .fetch()
        val hasNext = results.size >= pageable.pageSize

        return SliceImpl(results, pageable, hasNext)
    }


}