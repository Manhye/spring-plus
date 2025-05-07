package org.example.expert.domain.manager.dto.request

import org.jetbrains.annotations.NotNull

data class ManagerSaveRequest(
    @field:NotNull
    val managerUserId: Long
)
