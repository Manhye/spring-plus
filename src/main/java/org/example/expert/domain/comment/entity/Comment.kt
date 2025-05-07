package org.example.expert.domain.comment.entity

import jakarta.persistence.*
import org.example.expert.domain.common.entity.TimestampedKt
import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.user.entity.User
import org.example.expert.domain.user.enums.UserRole

@Entity
@Table(name = "comments")
class Comment(

    @Column(nullable = false)
    var contents: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    var todo: Todo
) : TimestampedKt() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    protected constructor() : this("", User("","", UserRole.USER), Todo("기본 제목", "기본 내용", null, User("a@a.com","password", UserRole.USER)))
}