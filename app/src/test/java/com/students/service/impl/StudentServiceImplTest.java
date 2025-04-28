package com.students.service.impl;

import com.students.dto.RequestStudentDTO;
import com.students.dto.ResponseStudentDTO;
import com.students.model.Student;
import com.students.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student testStudent;
    private RequestStudentDTO testRequestDTO;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("John Doe");
        testStudent.setAge(20);

        testRequestDTO = new RequestStudentDTO();
        testRequestDTO.setName("John Doe");
        testRequestDTO.setAge(20);
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() {
        // Arrange
        List<Student> students = Arrays.asList(testStudent);
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        List<ResponseStudentDTO> result = studentService.getAllStudents();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testStudent.getId(), result.get(0).getId());
        assertEquals(testStudent.getName(), result.get(0).getName());
        assertEquals(testStudent.getAge(), result.get(0).getAge());
        verify(studentRepository).findAll();
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() {
        // Arrange
        Student newStudent = new Student();
        newStudent.setName(testRequestDTO.getName());
        newStudent.setAge(testRequestDTO.getAge());
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        // Act
        ResponseStudentDTO result = studentService.createStudent(testRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testStudent.getId(), result.getId());
        assertEquals(testStudent.getName(), result.getName());
        assertEquals(testStudent.getAge(), result.getAge());
        verify(studentRepository).save(any(Student.class));
    }
} 