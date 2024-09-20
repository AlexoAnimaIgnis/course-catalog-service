package com.kotlinspring.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDto(
    val id: Int?,
    @get:NotBlank(message = "instructorDto.name must not be blank")
    var name: String
)
