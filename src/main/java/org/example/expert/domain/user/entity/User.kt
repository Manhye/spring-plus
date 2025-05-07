package org.example.expert.domain.user.entity

import jakarta.persistence.*
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.common.entity.TimestampedKt
import org.example.expert.domain.user.enums.UserRole

@Entity
@Table(name = "users",
    indexes = [Index(name = "idx_nickname", columnList = "nickname")]
)
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    var email: String,

    var password: String? = null,

    @Enumerated(EnumType.STRING)
    var userRole: UserRole,

    var nickname: String? = null
) : TimestampedKt() {

    companion object {
        fun fromAuthUser(authUser: AuthUser): User {
            return User(
                id = authUser.id,
                email = authUser.email,
                userRole = authUser.userRole,
                nickname = authUser.nickname
            )
        }
    }

    fun changePassword(password: String) {
        this.password = password
    }

    fun updateRole(userRole: UserRole){
        this.userRole = userRole
    }

    fun changeNickname(nickname: String) {
        this.nickname = nickname
    }

    constructor(email: String, password: String, userRole: UserRole) :
            this(null, email=email, password=password, userRole=userRole)

    constructor() : this(id = null, email = "", password = null, userRole = UserRole.USER)

}