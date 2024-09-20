package com.kotlinspring.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "INSTRUCTORS")
data class Instructor(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    // to connect to courseEntity
    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var courses : List<Course> = mutableListOf()
)
