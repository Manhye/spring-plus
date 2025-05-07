package org.example.expert.domain.comment.service

import org.example.expert.domain.comment.dto.request.CommentSaveRequest
import org.example.expert.domain.comment.dto.response.CommentResponse
import org.example.expert.domain.comment.dto.response.CommentSaveResponse
import org.example.expert.domain.comment.entity.Comment
import org.example.expert.domain.comment.repository.CommentRepository
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.common.exception.InvalidRequestException
import org.example.expert.domain.todo.repository.TodoRepository
import org.example.expert.domain.user.dto.response.UserResponse
import org.example.expert.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun saveComment(
        authUser: AuthUser,
        todoId: Long,
        request: CommentSaveRequest
    ): CommentSaveResponse {
        val user = userRepository.findById(authUser.id)
            .orElseThrow { InvalidRequestException("User not found") }

        val todo = todoRepository.findById(todoId)
            .orElseThrow { InvalidRequestException("Todo not found") }

        val comment = Comment(
            contents = request.contents,
            user = user,
            todo = todo
        )

        val saved = commentRepository.save(comment)

        return CommentSaveResponse(
            id = saved.id!!,
            contents = saved.contents,
            user = UserResponse(user.id!!, user.email)
        )
    }

    fun getComments(todoId: Long): List<CommentResponse> {
        val comments = commentRepository.findByTodoIdWithUser(todoId)

        return comments.map {
            CommentResponse(
                id = it.id!!,
                contents = it.contents,
                user = UserResponse(it.user.id!!, it.user.email)
            )
        }
    }
}
