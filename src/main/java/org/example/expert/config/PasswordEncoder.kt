package org.example.expert.config

import at.favre.lib.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Component
class PasswordEncoder {

    fun encode(rawPassword: String): String {
        if (rawPassword.isBlank()) {
            throw IllegalArgumentException("Password cannot be null or empty")
        }
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray())
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        if (rawPassword.isBlank() || encodedPassword.isBlank()) {
            throw IllegalArgumentException("Password cannot be null or empty")
        }
        val result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword)
        return result.verified
    }
}