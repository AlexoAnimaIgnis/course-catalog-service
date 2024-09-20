package com.kotlinspring.entity

import jakarta.persistence.*

@Entity
@Table(name = "Courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    var category: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "INSTRUCTOR_ID",
        nullable = false
    ) // mandatory, join
    val instructor: Instructor? = null
)