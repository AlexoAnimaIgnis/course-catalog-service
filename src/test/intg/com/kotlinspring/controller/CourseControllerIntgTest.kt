package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient // scans controller and automatically make endpoints available,
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun addCourse() {
        val courseDto = CourseDto(null, "Build Restful API using Spring Boot and Kotlin","Alexo")

        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertNotNull(savedCourseDto!!.id)
    }
}