package com.pucetec.students.controllers

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.services.EnrollmentService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EnrollmentController (
    private val enrollmentService: EnrollmentService
){
    @PostMapping("/api/enrollments")
    fun createEnrollment(
        @RequestBody request: EnrollmentRequest
    ): EnrollmentResponse {
        return enrollmentService.createEnrollment(request)
    }

    @GetMapping("/api/enrollments")
    fun getAllEnrollments(): List<EnrollmentResponse> {
        return enrollmentService.getAllEnrollments()
    }

    @GetMapping("/api/enrollments/{id}")
    fun getEnrollmentById(
        @PathVariable id: Long
    ): EnrollmentResponse {
        return enrollmentService.getEnrollmentById(id)
    }

    @PutMapping("/api/enrollments/{id}")
    fun updateEnrollment(
        @PathVariable id: Long,
        @RequestBody request: EnrollmentRequest
    ): EnrollmentResponse {
        return enrollmentService.updateEnrollment(id, request)
    }

    @DeleteMapping("/api/enrollments/{id}")
    fun deleteEnrollment(
        @PathVariable id: Long
    ) {
        enrollmentService.deleteEnrollment(id)
    }
}