package com.foodexpress.authservice.config.security

import com.foodexpress.authservice.service.LogoutService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(private val authenticationProvider:AuthenticationProvider,
                            private val jwtAuthenticationFilter: JwtAuthenticationFilter,
                            private val logoutService: LogoutService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http
            .cors()
            .and()
            .csrf { it.disable() }
            .authorizeHttpRequests {
                    requests ->
                requests
                    .requestMatchers(AntPathRequestMatcher("/api/v1/users/**"))
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .sessionManagement {
                    sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .logout {logout->
                logout.logoutUrl("/api/v1/users/logout")
                logout.addLogoutHandler(logoutService)
                    .logoutSuccessHandler { _, _, _ -> SecurityContextHolder.clearContext() }
            }
            .build()

    }
}