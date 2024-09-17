package com.kotlinspring.service

import com.kotlinspring.dto.CourseDto
import com.kotlinspring.entity.Course
import com.kotlinspring.exception.CourseNotFoundException
import com.kotlinspring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object : KLogging()

    fun addCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseDto.let {
            Course(null, it.name, it.category)
        }
        courseRepository.save(courseEntity)

        logger.info("Saved course is $courseEntity")

        return courseEntity.let {
            CourseDto(it.id, it.name, it.category)
        }
    }

    fun retrieveAllCourses(courseName: String?): List<CourseDto> {

        val courses = courseName?.let {
            courseRepository.findCoursesByName(courseName)
        } ?: courseRepository.findAll()

        return courses.map {
            CourseDto(it.id, it.name, it.category)
        }
    }

    fun updateCourse(courseId: Int, courseDto: CourseDto): CourseDto {
        val existingCourse = courseRepository.findById(courseId)

        return if (existingCourse.isPresent) {
            existingCourse.get()
                .let {
                    it.name = courseDto.name
                    it.category = courseDto.category
                    courseRepository.save(it)
                    CourseDto(it.id, it.name, it.category)
                }
        } else {
            throw CourseNotFoundException("No course found for id: $courseId")
        }
    }

    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)

        return if (existingCourse.isPresent) {
            existingCourse.get()
                .let {
                    courseRepository.deleteById(courseId)
                }
        } else {
            throw CourseNotFoundException("No course found for id: $courseId")
        }
    }

}
