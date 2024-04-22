package com.foodexpress.authservice.controller

import com.foodexpress.authservice.service.TokenStoreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val tokenStoreService: TokenStoreService) {

    @GetMapping("/validate")
    fun validate(@RequestHeader("Authorization") token: String): Boolean = tokenStoreService.isTokenPresent(token)



}