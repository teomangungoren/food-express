package com.foodexpress.authservice.repository

import com.foodexpress.authservice.domain.model.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TokenRepository : JpaRepository<Token, String> {

    fun findByToken(token:String):Token?

    @Query("""
        select t
            from Token t
        where t.user.id = :id and (t.expired = false or t.revoked = false)
    """)
    fun findAllValidTokensByUser(id:String):List<Token>;
}