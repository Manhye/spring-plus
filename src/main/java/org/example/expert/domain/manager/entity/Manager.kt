package org.example.expert.domain.manager.entity

import jakarta.persistence.*
import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.user.entity.User


@Entity
@Table(name = "managers")
class Manager (

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    val todo: Todo

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    protected constructor() : this(user =  User(), todo = Todo())
}