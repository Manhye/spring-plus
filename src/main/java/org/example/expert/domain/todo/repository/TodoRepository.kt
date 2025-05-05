package org.example.expert.domain.todo.repository

import org.example.expert.domain.todo.entity.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface TodoRepository : JpaRepository<Todo, Long>, TodoRepositoryCustom {

    @Query(
        """
            SELECT t FROM Todo t
            WHERE (:weather IS NULL OR t.weather = :weather)
            AND (:startTime IS NULL OR t.modifiedAt >= :startTime)
            AND (:endTime IS NULL OR t.modifiedAt <= :endTime)
            ORDER BY t.modifiedAt DESC
        """
    )
    fun findByConditions(
        @Param("weather") weather: String?,
        @Param("startTime") startTime: LocalDateTime?,
        @Param("endTime") endTime: LocalDateTime?,
        pageable: Pageable
    ): Page<Todo>
}