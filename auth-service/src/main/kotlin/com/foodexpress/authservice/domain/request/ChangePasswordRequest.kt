package com.foodexpress.authservice.domain.request

data class ChangePasswordRequest(
    val username:String,
    val oldPassword:String,
    val newPassword:String
)