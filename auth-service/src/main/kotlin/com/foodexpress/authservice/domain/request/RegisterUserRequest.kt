package com.foodexpress.authservice.domain.request



data class RegisterUserRequest(
    val email:String,
    val username:String,
    val password:String
)