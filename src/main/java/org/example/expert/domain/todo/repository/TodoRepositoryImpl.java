package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

	private final EntityManager em;

	public Page<Todo> searchTodos(String weather, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable){
		StringBuilder jpql = new StringBuilder("Select t FROM Todo t WHERE 1=1");

		if(weather != null){
			jpql.append(" AND t.weather = :weather");
		}

		if(startTime != null && endTime != null){
			jpql.append(" AND t.modifiedAt BETWEEN :startTime AND :endTime");
		}else if(startTime != null){
			jpql.append(" AND t.modifiedAt >= :startTime");
		}else if(endTime != null){
			jpql.append(" AND t.modifiedAt <= :endTime");
		}

		jpql.append(" ORDER BY t.modifiedAt DESC");

		TypedQuery<Todo> query = em.createQuery(jpql.toString(), Todo.class);

		if(weather != null){
			query.setParameter("weather", weather);
		}

		if(startTime != null && endTime != null){
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
		}else if(startTime != null){
			query.setParameter("startTime", startTime);
		}else if(endTime != null){
			query.setParameter("endTime", endTime);
		}

		int total = query.getResultList().size();

		List<Todo> content =  query
			.setFirstResult((int)pageable.getOffset())
			.setMaxResults(pageable.getPageSize())
			.getResultList();

		return new PageImpl<>(content, pageable, total);
	}
}
