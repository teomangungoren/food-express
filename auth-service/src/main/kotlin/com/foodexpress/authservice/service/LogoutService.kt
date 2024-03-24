package com.foodexpress.authservice.service

import com.foodexpress.authservice.repository.TokenRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service

@Service
class LogoutService(private val tokenRepository: TokenRepository):LogoutHandler {

    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {

        val authHeader = request?.getHeader("Authorization")
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return
        }
        val jwt: String = authHeader.substring(7)
        tokenRepository.findByToken(jwt)?.apply{
            expired=true
            revoked=true
        }?.also{
            tokenRepository.save(it)
        }
    }

}