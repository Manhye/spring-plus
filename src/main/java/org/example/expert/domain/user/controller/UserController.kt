package org.example.expert.domain.user.controller

import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest
import org.example.expert.domain.user.dto.response.UserResponse
import org.example.expert.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {

    @GetMapping("/users/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUser(userId))
    }

    @PutMapping("/users")
    fun changePassword(
        @AuthenticationPrincipal authUser: AuthUser,
        @RequestBody userChangePasswordRequest: UserChangePasswordRequest
    ){
        userService.changePassword(authUser.id, userChangePasswordRequest)
    }

    @GetMapping("/users")
    fun getUserByNickname(
        @RequestParam nickname: String
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUserByNickname(nickname))
    }
}