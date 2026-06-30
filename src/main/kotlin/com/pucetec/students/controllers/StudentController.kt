package com.pucetec.students.controllers

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.services.StudentService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StudentController (
    val studentService: StudentService
){
    val logger = LoggerFactory.getLogger(StudentController::class.java)

    @PostMapping("/api/student")
    fun createStudent(
        @RequestBody request: StudentRequest
    ): StudentResponse {
        logger.info("Creating student ${request.name}")
        return studentService.createStudent(request)
    }

    @GetMapping("/api/student")
    fun getAllStudents(): List<StudentResponse> {
        logger.info("Retrieving all students")
        return studentService.getAllStudents()
    }

    @GetMapping("/api/student/{id}")
    fun getStudentById(
        @PathVariable id: Long
    ): StudentResponse {
        logger.info("Getting student with id $id")
        return studentService.getStudentById(id)
    }

    @PutMapping("/api/student/{id}")
    fun updateStudent(
        @PathVariable id: Long,
        @RequestBody request: StudentRequest
    ): StudentResponse {
        logger.info("Updating student with id $id")
        return studentService.updateStudent(id, request)
    }

    @DeleteMapping("/api/student/{id}")
    fun deleteStudent(
        @PathVariable id: Long
    ) {
        logger.info("Deleting student with id $id")
        studentService.deleteStudent(id)
    }
}