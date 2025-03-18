package com.example.apigateway.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint, ServerAuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException?) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write("{\"error\": \"Unauthorized\", \"message\": \"${authException?.message ?: "Invalid or missing token"}\"}")
    }

    override fun commence(exchange: ServerWebExchange?, ex: AuthenticationException?): Mono<Void> {
        TODO("Not yet implemented")
    }

}