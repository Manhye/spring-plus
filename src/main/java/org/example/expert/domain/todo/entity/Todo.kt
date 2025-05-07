package org.example.expert.domain.todo.entity

import jakarta.persistence.*
import org.example.expert.domain.comment.entity.Comment
import org.example.expert.domain.manager.entity.Manager
import org.example.expert.domain.user.entity.User
import org.example.expert.domain.common.entity.TimestampedKt

@Entity
@Table(name = "todos")
class Todo  (
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var contents: String,

    var weather: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User

) : TimestampedKt() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "todo", cascade = [CascadeType.REMOVE])
    var comments: MutableList<Comment> = mutableListOf()

    @OneToMany(mappedBy = "todo", cascade = [CascadeType.ALL], orphanRemoval = true)
    var managers: MutableList<Manager> = mutableListOf()

    init{
        managers.add(Manager(user, this))
    }


    constructor() : this(title = "", contents = "", weather = null, user = User())
}