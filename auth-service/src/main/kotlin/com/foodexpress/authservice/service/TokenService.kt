package com.foodexpress.authservice.service

import com.foodexpress.authservice.domain.model.Token
import com.foodexpress.authservice.domain.model.User
import com.foodexpress.authservice.repository.TokenRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date

@Service
class TokenService(private val tokenRepository: TokenRepository){

    fun extractUsername(token:String):String{
        return extractClaim(token,Claims::getSubject)
    }

    fun generateToken(user:User):String{
        val claims= mapOf(
            "roles" to user.authorities.joinToString { it.authority },
            "userId" to user.id,
            "username" to user.username
        )
        return buildToken(claims,user)
    }

    private fun buildToken(extractClaims:Map<String,Any>,userDetails:UserDetails):String{
        return Jwts
            .builder()
            .setClaims(extractClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis()+100*60*60))
            .signWith(getSignInKey(),SignatureAlgorithm.HS256)
            .compact()
    }

    fun isValidToken(token:String,userDetails:UserDetails):Boolean{
        val username=extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token:String): Boolean{
        return extractExpiration(token).before(Date())
    }

    fun extractExpiration(token: String): Date{
        return extractClaim(token,Claims::getExpiration)
    }

    fun extractAllClaims(token:String):Claims{
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun <T> extractClaim(token:String,claimsResolver:(Claims)->T):T{
        val claims=extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun getSignInKey() : Key{
        val keyBytes= Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun getValidTokensByUser(id:String):List<Token>{
        return tokenRepository.findAllValidTokensByUser(id);
    }



    companion object {
        const val SECRET_KEY: String = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
    }
}