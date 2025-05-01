package org.example.expert.domain.todo.repository;

import java.util.Optional;

import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public TodoRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<Todo> findByIdWithUser(long todoId) {
		QTodo qTodo = QTodo.todo;
		QUser qUser = QUser.user;

		Todo todo = queryFactory.selectFrom(qTodo)
			.leftJoin(qTodo.user, qUser).fetchJoin() // fetchJoin을 사용하여 N+1 문제 해결
			.where(qTodo.id.eq(todoId))
			.fetchOne();

		return Optional.ofNullable(todo);
	}
}