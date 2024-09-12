package com.kotlinspring.util

import com.kotlinspring.dto.CourseDto
import com.kotlinspring.entity.Course

fun courseEntityList() = listOf(
    Course(null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development"),
    Course(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        ,
    ),
    Course(null,
        "Wiremock for Java Developers", "Development" ,
    )
)

fun courseDTO(
    id: Int? = null,
    name: String = "Build RestFul APis using Spring Boot and Kotlin",
    category: String = "Development",
    //instructorId: Int? = 1
) = CourseDto(
    id,
    name,
    category,
    //instructorId
)