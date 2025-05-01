package org.example.expert.domain.todo.repository;

import static org.example.expert.domain.comment.entity.QComment.*;
import static org.example.expert.domain.manager.entity.QManager.*;
import static org.example.expert.domain.todo.entity.QTodo.*;
import static org.example.expert.domain.user.entity.QUser.*;

import java.util.List;
import java.util.Optional;

import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public TodoRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<Todo> findByIdWithUser(long todoId) {
		QTodo qTodo = todo;
		QUser qUser = user;

		Todo todo = queryFactory.selectFrom(qTodo)
			.leftJoin(qTodo.user, qUser).fetchJoin()
			.where(qTodo.id.eq(todoId))
			.fetchOne();

		return Optional.ofNullable(todo);
	}

	@Override
	public Page<TodoSearchResponse> searchTodos(TodoSearchRequest request, Pageable pageable) {

		List<TodoSearchResponse> results = queryFactory
			.select(Projections.constructor(
				TodoSearchResponse.class,
				todo.title,
				manager.countDistinct(),
				comment.countDistinct()
			))
			.from(todo)
			.leftJoin(todo.managers, manager)
			.leftJoin(todo.comments, comment)
			.leftJoin(manager.user, user)
			.where(
				request.getTitleKeyword() != null ? todo.title.contains(request.getTitleKeyword()) : null,
				request.getManagerNickname() != null ? user.nickname.contains(request.getManagerNickname()) : null,
				request.getStartDate() != null ? todo.createdAt.goe(request.getStartDate().atStartOfDay()) : null,
				request.getEndDate() !=  null? todo.createdAt.loe(request.getEndDate().atTime(23,59,59)) : null
			)
			.groupBy(todo.id)
			.orderBy(todo.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		var countQuery = queryFactory
			.select(todo.id.countDistinct())
			.from(todo)
			.leftJoin(manager.user, user)
			.where(
				request.getTitleKeyword() != null ? todo.title.contains(request.getTitleKeyword()) : null,
				request.getManagerNickname() != null ? user.nickname.contains(request.getManagerNickname()) : null,
				request.getStartDate() != null ? todo.createdAt.goe(request.getStartDate().atStartOfDay()) : null,
				request.getEndDate() != null ? todo.createdAt.loe(request.getEndDate().atTime(23, 59, 59)) : null
			);

		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
	}
}