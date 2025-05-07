package org.example.expert.config

import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.user.enums.UserRole
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun  doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ){
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            val token = jwtUtil.substringToken(bearerToken)
            try{
                val claims: Claims = jwtUtil.extractClaims(token)
                if(claims != null){
                    val userId = claims.subject.toLong()
                    val email = claims["email", String::class.java]
                    val nickname = claims["nickname", String::class.java]
                    val userRole = claims["userRole", String::class.java]

                    val authorities: List<GrantedAuthority> = listOf(
                        SimpleGrantedAuthority("ROLE_$userRole")
                    )

                    val authUser = AuthUser(userId, email, UserRole.of(userRole), nickname)

                    val authentication = UsernamePasswordAuthenticationToken(authUser, null, authorities)

                    SecurityContextHolder.getContext().authentication = authentication
                }
            } catch (e: Exception){
                log.error("JWT authentication error", e)
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 인증 실패")
                return
            }
        }
        filterChain.doFilter(request, response)

    }
}