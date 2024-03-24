package com.foodexpress.authservice.domain.request

import com.foodexpress.authservice.domain.model.Adress


data class RegisterUserRequest(
    val email:String,
    val username:String,
    val password:String,
    val billingAddress: Adress
)