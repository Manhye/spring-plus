package org.example.expert.aop

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.example.expert.domain.common.dto.AuthUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Aspect
@Component
class AdminAccessLoggingAspect(private val request: HttpServletRequest) {

    private val log: Logger = LoggerFactory.getLogger(AdminAccessLoggingAspect::class.java)

    @Before("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    fun logBeforeChangeUserRole(joinPoint: JoinPoint) {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        var userId = "NONE"

        if(authentication != null && authentication.principal is AuthUser){
            val authUser = authentication.principal as AuthUser
            userId = authUser.id.toString()
        }

        val requestUrl = request.requestURI
        val requestTime = LocalDateTime.now()

        log.info("Admin Access Log - User ID: {}, Request Time: {} Request URL: {}, Method: {}",
            userId, requestTime, requestUrl, joinPoint.signature.name)
    }
}