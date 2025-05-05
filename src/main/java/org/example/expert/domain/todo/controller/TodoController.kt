package org.example.expert.domain.todo.controller

import jakarta.validation.Valid
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.todo.dto.request.TodoFindRequest
import org.example.expert.domain.todo.dto.request.TodoSaveRequest
import org.example.expert.domain.todo.dto.request.TodoSearchRequest
import org.example.expert.domain.todo.dto.response.TodoResponse
import org.example.expert.domain.todo.dto.response.TodoSaveResponse
import org.example.expert.domain.todo.dto.response.TodoSearchResponse
import org.example.expert.domain.todo.service.TodoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class TodoController (
    private val todoService: TodoService
){
    @PostMapping("/todos")
    fun saveTodo(
        @AuthenticationPrincipal authUser: AuthUser,
        @Valid @RequestBody todoSaveRequest: TodoSaveRequest
    ): ResponseEntity<TodoSaveResponse>{
        return ResponseEntity.ok(todoService.saveTodo(authUser, todoSaveRequest))
    }

    @GetMapping("/todos")
    fun getTodos(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @Valid @RequestBody todoFindRequest: TodoFindRequest
    ): ResponseEntity<Page<TodoResponse>>{
        return ResponseEntity.ok(todoService.getTodos(page, size, todoFindRequest))
    }

    @GetMapping("/todos/{todoId}")
    fun getTodo(
        @PathVariable todoId: Long
    ): ResponseEntity<TodoResponse>{
        return ResponseEntity.ok(todoService.getTodo(todoId))
    }

    @GetMapping("/todos/search")
    fun searchTodos(
        @ModelAttribute request: TodoSearchRequest,
        pageable: Pageable
    ): Page<TodoSearchResponse> {
        return todoService.searchTodos(request, pageable)
    }
}