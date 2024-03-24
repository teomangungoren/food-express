package com.foodexpress.authservice.domain.response

import com.foodexpress.authservice.domain.model.Adress
import java.time.Instant

data class UserResponse(
    val id:String,
    val username:String,
    val email:String,
    val createdAt:Instant,
    val adress:Adress
)