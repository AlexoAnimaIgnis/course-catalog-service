package com.kotlinspring.service

import com.kotlinspring.dto.InstructorDto
import com.kotlinspring.entity.Instructor
import com.kotlinspring.repository.InstructorRepository
import org.springframework.stereotype.Service


@Service
class InstructorService(val instructorRepository: InstructorRepository) {

    fun createInstructor(instructorDTO: InstructorDto): InstructorDto {

        val instructorEntity = instructorDTO.let {
            Instructor(it.id, it.name)
        }

        instructorRepository.save(instructorEntity)

        return instructorEntity.let {
            InstructorDto(it.id, it.name)
        }

    }

}
