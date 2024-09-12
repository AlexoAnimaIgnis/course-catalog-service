package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDto
import com.kotlinspring.entity.Course
import com.kotlinspring.service.CourseService
import com.kotlinspring.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@WebMvcTest(controllers = [CourseController::class]) // takes care of spinning up the controller
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {
        val courseDto = CourseDto(null, "Build Restful API using Spring Boot and Kotlin", "Alexo")

        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

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

    @Test
    fun addCourse_validation() {
        val courseDto = CourseDto(null, "", "")

        val errorResponse = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody
        assertEquals("courseDto.category must not be blank, courseDto.name must not be blank", errorResponse)
    }

    @Test
    fun addCourse_runTimeException() {
        val courseDto = CourseDto(null, "Build Restful API using Spring Boot and Kotlin", "Alexo")

        val errorMessage = "Unexpected Error occurred"
        every { courseServiceMock.addCourse(any()) } throws RuntimeException(errorMessage)

        val errorResponse = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody
        assertEquals(errorMessage, errorResponse)
    }

    @Test
    fun retrieveAllCourses() {

        every { courseServiceMock.retrieveAllCourses() }.returnsMany(
            listOf(
                courseDTO(id = 1),
                courseDTO(
                    id = 2,
                    name = "Build Reactive Microservices using Spring WebFlux/SpringBoot"
                )
            )
        )

        val courseDtos = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertEquals(2, courseDtos!!.size)
    }

    @Test
    fun updateCourse() {

        val course = Course(
            null,
            "Build RestFul APis using SpringBoot and Kotlin", "Development"
        )

        every { courseServiceMock.updateCourse(any(), any()) } returns courseDTO(
            id = 100,
            name = "Build RestFul APis using SpringBoot and Kotlin2"
        )

        val updatedCourseDto = Course(
            null,
            "Build RestFul APis using SpringBoot and Kotlin2", "Development"
        )

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{course_id}", 100)
            .bodyValue(updatedCourseDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertEquals("Build RestFul APis using SpringBoot and Kotlin2", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {

        every { courseServiceMock.deleteCourse(any()) } just runs

        val updatedCourse = webTestClient.delete()
            .uri("/v1/courses/{course_id}", 100)
            .exchange()
            .expectStatus().isNoContent

    }
}