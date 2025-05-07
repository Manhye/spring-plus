package org.example.expert.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.example.expert.domain.common.exception.ServerException
import org.example.expert.domain.user.enums.UserRole
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.Key
import java.util.*

@Component
class JwtUtil {

    companion object {
        private const val BEARER_PREFIX = "Bearer "
        private const val TOKEN_TIME = 60 * 60 * 1000L
    }

    @Value("\${jwt.secret.key}")
    private lateinit var secretKey: String
    private lateinit var key: Key
    private val signatureAlgorithm = SignatureAlgorithm.HS512

    @PostConstruct
    fun init(){
        val bytes =  Base64.getDecoder().decode(secretKey)
        key = Keys.hmacShaKeyFor(bytes)
    }

    fun createToken(userId: Long?, email: String, userRole: UserRole, nickname: String = "none"): String{
        val date = Date()
        return  "$BEARER_PREFIX" +
                Jwts.builder()
                    .setSubject(userId.toString())
                    .claim("email", email)
                    .claim("userRole", userRole)
                    .claim("nickname", nickname)
                    .setExpiration(Date(date.time + TOKEN_TIME))
                    .setIssuedAt(date)
                    .signWith(key, signatureAlgorithm)
                    .compact()
    }

    fun substringToken(tokenValue: String): String {
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)){
            return tokenValue.substring(7)
        }

        throw ServerException("Not Found Token")
    }

    fun extractClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}