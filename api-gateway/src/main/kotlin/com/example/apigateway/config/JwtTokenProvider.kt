package com.example.apigateway.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

// 토큰 검증만 구현 (createToken은 없음)
@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    private val secretKeyString: String
) {

    private val key: Key = Keys.hmacShaKeyFor(secretKeyString.toByteArray())

    fun getAuthentication(token: String): Authentication {
        val claims: Claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        val username = claims.subject
        val roles = claims["roles"] as? List<*> ?: emptyList<Any>()
        val authorities = roles.map { SimpleGrantedAuthority(it.toString()) }
        return UsernamePasswordAuthenticationToken(username, token, authorities)
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}