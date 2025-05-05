package org.example.expert.domain.todo.dto.request

import java.time.LocalDateTime

data class TodoFindRequest (
    val weather: String?,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?
)