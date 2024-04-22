package com.foodexpress.authservice.service

import com.foodexpress.authservice.repository.TokenRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class TokenStoreService(private val tokenRepository: TokenRepository,
                        private val redisTemplate: RedisTemplate<String, String>) {

    fun isTokenPresent(token: String): Boolean {
        val presentToken=tokenRepository.findByToken(token);
        return presentToken!=null && !presentToken.expired && !presentToken.revoked;
    }

}