package org.example.expert.domain.log.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "logs")
data class Log(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val userId: Long?,

    val details: String?,

    @Enumerated(EnumType.STRING)
    val result: LogResult,

    @CreatedDate
    val createdAt: LocalDateTime? = null,
) {
    constructor(userId: Long?, details: String?, result: LogResult) : this(
        userId = userId,
        details = details,
        result = result,
        createdAt = LocalDateTime.now()
    )
}