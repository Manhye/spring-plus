package org.example.expert.domain.user.repository

import org.example.expert.domain.user.dto.response.UserResponse
import org.example.expert.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    @Query(
        "SELECT new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email) " +
        "FROM User u WHERE u.nickname = :nickname"
    )
    fun findByNickname(@Param("nickname") nickname: String): UserResponse?
}