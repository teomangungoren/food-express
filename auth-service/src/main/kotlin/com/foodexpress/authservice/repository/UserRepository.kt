package com.foodexpress.authservice.repository

import com.foodexpress.authservice.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>{

    fun findByEmail(mail:String):User

    fun findByUsername(username:String):User
}
