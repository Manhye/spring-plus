package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepositoryCustom {
	Page<Todo> searchTodos(String weather, LocalDateTime start, LocalDateTime end, Pageable pageable);

}
