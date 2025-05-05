package org.example.expert.domain.todo.dto.request

import java.time.LocalDate

data class TodoSearchRequest (
    val titleKeyword: String? = null,
    val managerNickname: String? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null
)