package com.foodexpress.authservice.config.security

import com.foodexpress.authservice.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ApplicationConfiguration(private val userRepository: UserRepository) {

    @Bean
    fun userDetailsService():UserDetailsService{
        return UserDetailsService { username ->
            userRepository.findByUsername(username)
                ?: throw RuntimeException("User not found")
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(config:AuthenticationConfiguration):AuthenticationManager{
        return config.authenticationManager
    }

    @Bean
    fun authenticationProvider():AuthenticationProvider{
        val authenticationProvider=DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService())
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    fun corsConfigurer():WebMvcConfigurer?{
        return object : WebMvcConfigurer{
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOriginPatterns("*")
                    .allowedOrigins("http://localhost:3000").allowedMethods("*").allowedHeaders("*").allowCredentials(true)
            }
        }
    }
}