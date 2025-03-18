package com.example.userservice.config


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secretKeyString: String,
    @Value("\${jwt.validityInMs:900000}") private val accessTokenValidityInMs: Long,       // 기본 15분
    @Value("\${jwt.refreshValidityInMs:604800000}") private val refreshTokenValidityInMs: Long  // 기본 1주일
) {
    private val key: Key = Keys.hmacShaKeyFor(secretKeyString.toByteArray())

    // 공통 토큰 생성 로직
    private fun createToken(username: String, roles: List<String>, validityInMs: Long): String {
        val claims: Claims = Jwts.claims().setSubject(username).apply {
            put("roles", roles)
        }
        val now = Date()
        val validity = Date(now.time + validityInMs)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    // Access Token 생성
    fun createAccessToken(username: String, roles: List<String>): String {
        return createToken(username, roles, accessTokenValidityInMs)
    }

    // Refresh Token 생성
    fun createRefreshToken(username: String, roles: List<String>): String {
        return createToken(username, roles, refreshTokenValidityInMs)
    }

    // 토큰으로부터 인증 객체 생성
    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        val username = claims.subject
        val roles = claims["roles"] as? List<*> ?: emptyList<Any>()
        val authorities = roles.map { SimpleGrantedAuthority(it.toString()) }
        return UsernamePasswordAuthenticationToken(username, token, authorities)
    }

    // 토큰 유효성 검사
    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    // Refresh Token을 사용해 새로운 Access Token 발급
    fun refreshAccessToken(refreshToken: String): String? {
        return if (validateToken(refreshToken)) {
            val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).body
            val username = claims.subject
            val roles = claims["roles"] as? List<*> ?: emptyList<Any>()
            createAccessToken(username, roles.map { it.toString() })
        } else {
            null
        }
    }
}