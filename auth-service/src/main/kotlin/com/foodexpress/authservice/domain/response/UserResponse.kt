package com.foodexpress.authservice.domain.response

import java.time.Instant

data class UserResponse(
    val id:String,
    val username:String,
    val email:String,
    val createdDate:Instant,
)