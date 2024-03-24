package com.foodexpress.authservice.domain.response

import java.util.Date

data class TokenResponse(
    val token : String,
    val username : String,
    val expirationDate : Date
)
