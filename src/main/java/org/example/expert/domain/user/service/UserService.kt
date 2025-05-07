package org.example.expert.domain.user.service

import org.example.expert.config.PasswordEncoder
import org.example.expert.domain.common.exception.InvalidRequestException
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest
import org.example.expert.domain.user.dto.response.UserResponse
import org.example.expert.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
){

    fun getUser(userId: Long): UserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow{
                InvalidRequestException("User not found")
            }
        return UserResponse(user.id, user.email)
    }

    @Transactional
    fun changePassword(userId: Long, userChangePasswordRequest: UserChangePasswordRequest){
        validateNewPassword(userChangePasswordRequest)

        val user = userRepository.findById(userId)
            .orElseThrow{
                InvalidRequestException("User not found")
            }
        if(passwordEncoder.matches(userChangePasswordRequest.newPassword, user.password)){
            throw InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.")
        }

        if(!passwordEncoder.matches(userChangePasswordRequest.oldPassword, user.password)){
            throw InvalidRequestException("잘못된 비밀번호입니다.")
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.newPassword))
    }

    private fun validateNewPassword(userChangePasswordRequest: UserChangePasswordRequest){
        val newPassword = userChangePasswordRequest.newPassword
        if(newPassword.length < 8 || !newPassword.matches(".*\\d.*".toRegex()))
            throw InvalidRequestException("새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.")
    }

    fun getUserByNickname(nickname: String): UserResponse {
        return userRepository.findByNickname(nickname)
            ?: throw InvalidRequestException("User not found")
    }
}