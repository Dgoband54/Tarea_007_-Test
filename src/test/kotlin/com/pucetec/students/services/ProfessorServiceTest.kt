package com.pucetec.students.services

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.entities.Professor
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.ProfessorNotFound
import com.pucetec.students.repositories.ProfessorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class ProfessorServiceTest {

    @Mock
    private lateinit var professorRepository: ProfessorRepository

    @InjectMocks
    private lateinit var professorService: ProfessorService

    @Test
    fun `createProfessor should throw BlankNameException when name is blank`() {
        val request = ProfessorRequest(name = "", email = "kakashi.hatake@puce.edu.ec")

        assertThrows(BlankNameException::class.java) {
            professorService.createProfessor(request)
        }
    }

    @Test
    fun `createProfessor should return valid ProfessorResponse when name is not blank`() {
        val request = ProfessorRequest(
            name = "Kakashi Hatake",
            email = "kakashi.hatake@puce.edu.ec"
        )

        val savedProfessor = Professor(
            id = 1L,
            name = "Kakashi Hatake",
            email = "kakashi.hatake@puce.edu.ec"
        )

        `when`(professorRepository.save(any(Professor::class.java)))
            .thenReturn(savedProfessor)

        val response = professorService.createProfessor(request)

        assertEquals(1L, response.id)
        assertEquals("Kakashi Hatake", response.name)
        assertEquals("kakashi.hatake@puce.edu.ec", response.email)
    }

    @Test
    fun `getAllProfessors should return a list of ProfessorResponse`() {
        val professors = listOf(
            Professor(
                id = 1L,
                name = "Satoru Gojo",
                email = "satoru.gojo@puce.edu.ec"
            )
        )

        `when`(professorRepository.findAll())
            .thenReturn(professors)

        val response = professorService.getAllProfessors()

        assertEquals(1, response.size)
        assertEquals("Satoru Gojo", response[0].name)
    }

    @Test
    fun `getProfessorById should return a ProfessorResponse`() {
        val professor = Professor(
            id = 1L,
            name = "Satoru Gojo",
            email = "satoru.gojo@puce.edu.ec"
        )

        `when`(professorRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.of(professor))

        val response = professorService.getProfessorById(1L)

        assertEquals(1L, response.id)
        assertEquals("Satoru Gojo", response.name)
    }

    @Test
    fun `getProfessorById should throw a ProfessorNotFound exception`() {
        `when`(professorRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.empty())

        assertThrows(ProfessorNotFound::class.java) {
            professorService.getProfessorById(1L)
        }
    }

    @Test
    fun `updateProfessor should throw ProfessorNotFound when id does not exist`() {
        val request = ProfessorRequest(name = "Satoru Gojo", email = "satoru.gojo@puce.edu.ec")

        `when`(professorRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.empty())

        assertThrows(ProfessorNotFound::class.java) {
            professorService.updateProfessor(1L, request)
        }
    }

    @Test
    fun `updateProfessor should throw BlankNameException when name is blank`() {
        val request = ProfessorRequest(name = "", email = "satoru.gojo@puce.edu.ec")
        val professor = Professor(
            id = 1L,
            name = "Satoru Gojo",
            email = "satoru.gojo@puce.edu.ec"
        )

        `when`(professorRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.of(professor))

        assertThrows(BlankNameException::class.java) {
            professorService.updateProfessor(1L, request)
        }
    }

    @Test
    fun `updateProfessor should return valid ProfessorResponse when data is correct`() {
        val request = ProfessorRequest(name = "Suguru Geto", email = "suguru.geto@puce.edu.ec")
        val existingProfessor = Professor(
            id = 1L,
            name = "Satoru Gojo",
            email = "satoru.gojo@puce.edu.ec"
        )
        val updatedProfessor = Professor(
            id = 1L,
            name = "Suguru Geto",
            email = "suguru.geto@puce.edu.ec"
        )

        `when`(professorRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.of(existingProfessor))
        `when`(professorRepository.save(any(Professor::class.java)))
            .thenReturn(updatedProfessor)

        val response = professorService.updateProfessor(1L, request)

        assertEquals(1L, response.id)
        assertEquals("Suguru Geto", response.name)
    }

    @Test
    fun `deleteProfessor should throw ProfessorNotFound when id does not exist`() {
        `when`(professorRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.empty())

        assertThrows(ProfessorNotFound::class.java) {
            professorService.deleteProfessor(1L)
        }
    }

    @Test
    fun `deleteProfessor should delete professor when id exists`() {
        val professor = Professor(
            id = 1L,
            name = "Satoru Gojo",
            email = "satoru.gojo@puce.edu.ec"
        )

        `when`(professorRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.of(professor))

        professorService.deleteProfessor(1L)

        verify(professorRepository).delete(professor)
    }
}
