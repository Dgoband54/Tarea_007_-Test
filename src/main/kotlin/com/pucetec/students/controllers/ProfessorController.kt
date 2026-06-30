package com.pucetec.students.controllers

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.dto.ProfessorResponse
import com.pucetec.students.services.ProfessorService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfessorController (
    val professorService: ProfessorService
){
    val logger = LoggerFactory.getLogger(ProfessorController::class.java)

    @PostMapping("/api/professor")
    fun createProfessor(
        @RequestBody request: ProfessorRequest
    ): ProfessorResponse {
        logger.info("Creating professor ${request.name}")
        return professorService.createProfessor(request)
    }

    @GetMapping("/api/professor")
    fun getAllProfessors(): List<ProfessorResponse> {
        logger.info("Retrieving all professors")
        return professorService.getAllProfessors()
    }

    @GetMapping("/api/professor/{id}")
    fun getProfessorById(
        @PathVariable id: Long
    ): ProfessorResponse {
        logger.info("Getting professor with id $id")
        return professorService.getProfessorById(id)
    }

    @PutMapping("/api/professor/{id}")
    fun updateProfessor(
        @PathVariable id: Long,
        @RequestBody request: ProfessorRequest
    ): ProfessorResponse {
        logger.info("Updating professor with id $id")
        return professorService.updateProfessor(id, request)
    }

    @DeleteMapping("/api/professor/{id}")
    fun deleteProfessor(
        @PathVariable id: Long
    ) {
        logger.info("Deleting professor with id $id")
        professorService.deleteProfessor(id)
    }
}