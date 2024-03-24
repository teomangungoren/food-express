package com.foodexpress.authservice.domain.mapper

import com.foodexpress.authservice.config.MappingConfiguration
import com.foodexpress.authservice.domain.model.User
import com.foodexpress.authservice.domain.response.UserResponse
import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter


@Mapper(config= MappingConfiguration::class)
interface UserMapper : Converter<User,UserResponse> {
    override fun convert(source: User): UserResponse
}