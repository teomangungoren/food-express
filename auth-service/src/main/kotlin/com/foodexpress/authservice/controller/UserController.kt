package com.foodexpress.authservice.controller

import com.foodexpress.authservice.domain.model.User
import com.foodexpress.authservice.domain.request.ChangePasswordRequest
import com.foodexpress.authservice.domain.request.LoginRequest
import com.foodexpress.authservice.domain.request.RegisterUserRequest
import com.foodexpress.authservice.domain.response.TokenResponse
import com.foodexpress.authservice.domain.response.UserResponse
import com.foodexpress.authservice.service.TokenStoreService
import com.foodexpress.authservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService,
                    private val tokenStoreService: TokenStoreService){

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterUserRequest):ResponseEntity<UserResponse>{
        return  ResponseEntity(userService.register(request),HttpStatus.CREATED)
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody request:LoginRequest):ResponseEntity<TokenResponse>{
        return ResponseEntity(userService.login(request),HttpStatus.OK)
    }


    @PutMapping("/password")
    fun changePassword(@RequestBody changePasswordRequest: ChangePasswordRequest):ResponseEntity<*>{
        return ResponseEntity(userService.changePassword(changePasswordRequest),HttpStatus.OK)
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String):ResponseEntity<User>{
        return ResponseEntity(userService.getUserByUsername(username),HttpStatus.OK)
    }


    @GetMapping("/validateToken")
    fun validate(@RequestHeader("Authorization") token: String): Boolean = tokenStoreService.isTokenPresent(token)






}