package com.pucetec.students.services

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.entities.Student
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.repositories.StudentRepository
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
class StudentServiceTest {

    @Mock
    private lateinit var studentRepository: StudentRepository

    @InjectMocks
    private lateinit var studentService: StudentService

    @Test
    fun `createStudent should throw BlankNameException when name is blank`() {
        val request = StudentRequest(name = "", email = "naruto.uzumaki@puce.edu.ec")

        assertThrows(BlankNameException::class.java) {
            studentService.createStudent(request)
        }
    }

    @Test
    fun `createStudent should return valid StudentResponse when name is not blank`() {
        val request = StudentRequest(
            name = "Naruto Uzumaki",
            email = "naruto.uzumaki@puce.edu.ec"
        )

        val savedStudent = Student(
            id = 1L,
            name = "Naruto Uzumaki",
            email = "naruto.uzumaki@puce.edu.ec"
        )

        `when`(studentRepository.save(any(Student::class.java)))
            .thenReturn(savedStudent)

        val response = studentService.createStudent(request)

        assertEquals(1L, response.id)
        assertEquals("Naruto Uzumaki", response.name)
        assertEquals("naruto.uzumaki@puce.edu.ec", response.email)
    }

    @Test
    fun `createStudent should return valid StudentResponse with empty email when email is null`() {
        val request = StudentRequest(
            name = "Sakura Haruno",
            email = null
        )

        val savedStudent = Student(
            id = 1L,
            name = request.name,
            email = request.email
        )

        `when`(studentRepository.save(any(Student::class.java)))
            .thenReturn(savedStudent)

        val response = studentService.createStudent(request)

        assertEquals(1L, response.id)
        assertEquals("Sakura Haruno", response.name)
        assertEquals(null, response.email)
    }

    @Test
    fun `getAllStudents should return a list of StudentResponse `() {
        val students = listOf(
            Student (
                id = 1L,
                name = "Monkey Luffy",
                email = "monkey.luffy@puce.edu.ec"
            ),
            Student (
                id = 2L,
                name = "Roronoa Zoro",
                email = "roronoa.zoro@puce.edu.ec"
            ),
            Student (
                id = 3L,
                name = "Nami Bellemere",
                email = "nami.bellemere@puce.edu.ec"
            )

        )

        `when`(studentRepository.findAll())
            .thenReturn(students)

        val response = studentService.getAllStudents()

        assertEquals(3, response.size)
        assertEquals("Monkey Luffy", students[0].name)

    }
    @Test
    fun `getStudentById should return a StudentResponse `() {
        val student = Student(
                id = 1L,
                name = "Ichigo Kurosaki",
                email = "ichigo.kurosaki@puce.edu.ec"
        )

        `when`(studentRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.of(student))

        val response = studentService.getStudentById(1L)

        assertEquals(1L, response.id)

    }
    @Test
    fun `getStudentById should throw a StudentNotFoundException `() {

        `when`(studentRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.empty())

        assertThrows(StudentNotFoundException::class.java) {
            studentService.getStudentById(1L)
        }
    }
    @Test
    fun `updateStudent should throw StudentNotFoundException when id does not exist`() {
        val request = StudentRequest(name = "Hinata Hyuga", email = "hinata.hyuga@puce.edu.ec")

        `when`(studentRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.empty())

        assertThrows(StudentNotFoundException::class.java) {
            studentService.updateStudent(1L, request)
        }
    }

    @Test
    fun `updateStudent should throw BlankNameException when name is blank`() {
        val request = StudentRequest(name = "", email = "hinata.hyuga@puce.edu.ec")
        val student = Student(
            id = 1L,
            name = "Edward Elric",
            email = "edward.elric@puce.edu.ec"
        )

        `when`(studentRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.of(student))

        assertThrows(BlankNameException::class.java) {
            studentService.updateStudent(1L, request)
        }
    }

    @Test
    fun `updateStudent should return valid StudentResponse when data is correct`() {
        val request = StudentRequest(name = "Hinata Hyuga", email = "hinata.hyuga@puce.edu.ec")
        val existingStudent = Student(
            id = 1L,
            name = "Edward Elric",
            email = "edward.elric@puce.edu.ec"
        )
        val updatedStudent = Student(
            id = 1L,
            name = "Hinata Hyuga",
            email = "hinata.hyuga@puce.edu.ec"
        )

        `when`(studentRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.of(existingStudent))
        `when`(studentRepository.save(any(Student::class.java)))
            .thenReturn(updatedStudent)

        val response = studentService.updateStudent(1L, request)

        assertEquals(1L, response.id)
        assertEquals("Hinata Hyuga", response.name)
        assertEquals("hinata.hyuga@puce.edu.ec", response.email)
    }

    @Test
    fun `deleteStudent should throw StudentNotFoundException when id does not exist`() {
        `when`(studentRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.empty())

        assertThrows(StudentNotFoundException::class.java) {
            studentService.deleteStudent(1L)
        }
    }

    @Test
    fun `deleteStudent should delete student when id exists`() {
        val student = Student(
            id = 1L,
            name = "Levi Ackerman",
            email = "levi.ackerman@puce.edu.ec"
        )

        `when`(studentRepository.findById(any(Long::class.java)))
            .thenReturn(Optional.of(student))

        studentService.deleteStudent(1L)

        verify(studentRepository).delete(student)
    }

}
