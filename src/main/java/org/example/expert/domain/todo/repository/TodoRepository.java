//package org.example.expert.domain.todo.repository;
//
//import org.example.expert.domain.todo.entity.Todo;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import jakarta.persistence.TypedQuery;
//
//public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {
//
//    @Query("SELECT t FROM Todo t "
//        + "WHERE (:weather IS NULL OR t.weather = :weather) "
//        + "AND (:startTime IS NULL OR t.modifiedAt >= :start) "
//        + "AND (:endTime IS NULL OR t.modifiedAt <= :end) "
//        + "ORDER BY t.modifiedAt DESC")
//    Page<Todo> findByConditions(String weather, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
//}
