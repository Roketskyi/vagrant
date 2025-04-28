package com.students.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.dto.RequestStudentDTO;
import com.students.dto.ResponseStudentDTO;
import com.students.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseStudentDTO testStudent;
    private RequestStudentDTO testRequestDTO;

    @BeforeEach
    void setUp() {
        testStudent = new ResponseStudentDTO();
        testStudent.setId(1L);
        testStudent.setName("John Doe");
        testStudent.setAge(20);
        testStudent.setGrade(85.5);

        testRequestDTO = new RequestStudentDTO();
        testRequestDTO.setName("John Doe");
        testRequestDTO.setAge(20);
        testRequestDTO.setGrade(85.5);
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() throws Exception {
        // Arrange
        List<ResponseStudentDTO> students = Arrays.asList(testStudent);
        when(studentService.getAllStudents()).thenReturn(students);

        // Act & Assert
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(testStudent.getId()))
                .andExpect(jsonPath("$[0].name").value(testStudent.getName()))
                .andExpect(jsonPath("$[0].age").value(testStudent.getAge()))
                .andExpect(jsonPath("$[0].grade").value(testStudent.getGrade()));
    }

    @Test
    void getStudentById_ShouldReturnStudent() throws Exception {
        // Arrange
        when(studentService.getStudentById(1L)).thenReturn(testStudent);

        // Act & Assert
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testStudent.getId()))
                .andExpect(jsonPath("$.name").value(testStudent.getName()))
                .andExpect(jsonPath("$.age").value(testStudent.getAge()))
                .andExpect(jsonPath("$.grade").value(testStudent.getGrade()));
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() throws Exception {
        // Arrange
        when(studentService.createStudent(any(RequestStudentDTO.class))).thenReturn(testStudent);

        // Act & Assert
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testStudent.getId()))
                .andExpect(jsonPath("$.name").value(testStudent.getName()))
                .andExpect(jsonPath("$.age").value(testStudent.getAge()))
                .andExpect(jsonPath("$.grade").value(testStudent.getGrade()));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() throws Exception {
        // Arrange
        when(studentService.updateStudent(eq(1L), any(RequestStudentDTO.class))).thenReturn(testStudent);

        // Act & Assert
        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testStudent.getId()))
                .andExpect(jsonPath("$.name").value(testStudent.getName()))
                .andExpect(jsonPath("$.age").value(testStudent.getAge()))
                .andExpect(jsonPath("$.grade").value(testStudent.getGrade()));
    }

    @Test
    void deleteStudent_ShouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }
} 