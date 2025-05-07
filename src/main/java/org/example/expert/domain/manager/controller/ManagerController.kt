package org.example.expert.domain.manager.controller

import jakarta.validation.Valid
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest
import org.example.expert.domain.manager.dto.response.ManagerResponse
import org.example.expert.domain.manager.dto.response.ManagerSaveResponse
import org.example.expert.domain.manager.service.ManagerService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos/{todoId}/managers")
class ManagerController (
    private val managerService: ManagerService
){

    @PostMapping
    fun saveManager(
        @AuthenticationPrincipal authUser: AuthUser,
        @PathVariable todoId: Long,
        @Valid @RequestBody managerSaveRequest: ManagerSaveRequest
    ): ResponseEntity<ManagerSaveResponse>{
        return ResponseEntity.ok(managerService.saveManager(authUser,todoId,managerSaveRequest))
    }

    @GetMapping
    fun getMembers(@PathVariable todoId: Long): ResponseEntity<List<ManagerResponse>>{
        return ResponseEntity.ok(managerService.getManagers(todoId))
    }

    @DeleteMapping("/{managerId}")
    fun deleteManager(
        @AuthenticationPrincipal authUser: AuthUser,
        @PathVariable todoId: Long,
        @PathVariable managerId: Long
    ){
        managerService.deleteManager(authUser, todoId, managerId)
    }

}