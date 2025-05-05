package org.example.expert.domain.todo.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.example.expert.domain.comment.entity.QComment.comment
import org.example.expert.domain.manager.entity.QManager.manager
import org.example.expert.domain.todo.dto.request.TodoSearchRequest
import org.example.expert.domain.todo.dto.response.TodoSearchResponse
import org.example.expert.domain.todo.entity.QTodo.todo
import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.user.entity.QUser.user
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TodoRepositoryCustomImpl(
    em: EntityManager
) : TodoRepositoryCustom {

    private val queryFactory = JPAQueryFactory(em)

    override fun findByIdWithUser(todoId: Long): Optional<Todo> {
        val result = queryFactory
            .selectFrom(todo)
            .leftJoin(todo.user, user).fetchJoin()
            .where(todo.id.eq(todoId))
            .fetchOne()

        return Optional.ofNullable(result)
    }

    override fun searchTodos(request: TodoSearchRequest, pageable: Pageable): Page<TodoSearchResponse> {
        val results = queryFactory
            .select(
                Projections.constructor(
                    TodoSearchResponse::class.java,
                    todo.title,
                    manager.countDistinct(),
                    comment.countDistinct()
                )
            )
            .from(todo)
            .leftJoin(todo.managers, manager)
            .leftJoin(todo.comments, comment)
            .leftJoin(manager.user, user)
            .where(
                request.titleKeyword?.let{
                    todo.title.contains(it)
                },
                request.managerNickname?.let{
                    user.nickname.contains(it)
                },
                request.startDate?.let{
                    todo.createdAt.goe(it.atStartOfDay())
                },
                request.endDate?.let{
                    todo.createdAt.loe(it.atTime(23,59,59))
                }
            )
            .groupBy(todo.id)
            .orderBy(todo.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = queryFactory
            .select(todo.id.countDistinct())
            .from(todo)
            .leftJoin(manager.user, user)
            .where(
                request.titleKeyword?.let{
                    todo.title.contains(it)
                },
                request.managerNickname?.let{
                    user.nickname.contains(it)
                },
                request.startDate?.let{
                    todo.createdAt.goe(it.atStartOfDay())
                },
                request.endDate?.let{
                    todo.createdAt.loe(it.atTime(23,59,59))
                }
            )
        return PageableExecutionUtils.getPage(results,pageable){
            countQuery.fetchOne() ?: 0L
        }
    }

}