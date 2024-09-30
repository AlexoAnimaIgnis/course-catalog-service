package com.kotlinspring.controller

import com.kotlinspring.dto.InstructorDto
import com.kotlinspring.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(InstructorController::class)
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMock: InstructorService


    @Test
    fun addInstructor() {
        //given
        val instructorDTO = InstructorDto(null, "Dilip Sundarraj")

        every { instructorServiceMock.createInstructor(any()) } returns InstructorDto(1, "Dilip Sundarraj")

        val savedInstructorDTO = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDto::class.java)
            .returnResult()
            .responseBody

        //then
        Assertions.assertTrue {
            savedInstructorDTO!!.id != null
        }
    }

    @Test
    fun addInstructor_Validation() {
        //given
        val instructorDTO = InstructorDto(null, "")

        every { instructorServiceMock.createInstructor(any()) } returns InstructorDto(1, "Dilip Sundarraj")

        val response = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        //then
        assertEquals("instructorDTO.name must not be blank",response)
    }
}