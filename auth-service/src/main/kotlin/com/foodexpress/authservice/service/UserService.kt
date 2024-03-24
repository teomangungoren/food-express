package com.foodexpress.authservice.service

import com.foodexpress.authservice.domain.model.Adress
import com.foodexpress.authservice.domain.model.Token
import com.foodexpress.authservice.domain.model.User
import com.foodexpress.authservice.domain.request.ChangePasswordRequest
import com.foodexpress.authservice.domain.request.LoginRequest
import com.foodexpress.authservice.domain.request.RegisterUserRequest
import com.foodexpress.authservice.domain.response.TokenResponse
import com.foodexpress.authservice.domain.response.UserResponse
import com.foodexpress.authservice.repository.TokenRepository
import com.foodexpress.authservice.repository.UserRepository
import org.springframework.core.convert.ConversionService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository,
                  private val conversionService: ConversionService,
                  private val tokenService: TokenService,
                  private val authenticationManager: AuthenticationManager,
                  private val passwordEncoder: PasswordEncoder,
                  private val tokenRepository: TokenRepository
) {

    @Transactional
    fun register(request: RegisterUserRequest): UserResponse {
        val user = with(request) {
            val newUser = User(
                id = "",
                email = email,
                username = username,
                password = password,
                billingAdress = Adress(
                    street = billingAddress.street,
                    zipcode = billingAddress.zipcode,
                    city = billingAddress.city,
                )
            )
            userRepository.save(newUser)
        }
        return conversionService.convert(user, UserResponse::class.java)!!
    }

    @Transactional
    fun login(request:LoginRequest):TokenResponse{
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )
        val user=userRepository.findByUsername(request.username)
        val jwt=tokenService.generateToken(user)
        val expirationDate=tokenService.extractExpiration(jwt)
        revokeAllUserTokens(user)
        tokenRepository.save(Token("",jwt,false,false,user))
        return TokenResponse(jwt,request.username,expirationDate)
    }

     @Transactional
    fun changePassword(request: ChangePasswordRequest){
        val user=userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)
        require(passwordEncoder.matches(request.oldPassword,user.password)){ throw IllegalArgumentException("Old password is not correct") }
        require(request.newPassword.equals(request.oldPassword)){ throw IllegalArgumentException("New password and confirm password does not match") }
        user.password=passwordEncoder.encode(request.newPassword)
        userRepository.save(user)
    }


    private fun revokeAllUserTokens(user:User){
        val validTokens=tokenService.getValidTokensByUser(user.id)
        if(validTokens.isEmpty()){
            return
        }
        validTokens.forEach{
            it.revoked=true
            it.expired=true
        }
        tokenRepository.saveAll(validTokens)
    }
}