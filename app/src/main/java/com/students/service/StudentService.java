package com.students.service;

import com.students.dto.RequestStudentDTO;
import com.students.dto.ResponseStudentDTO;

import java.util.List;

public interface StudentService {
    List<ResponseStudentDTO> getAllStudents();
    ResponseStudentDTO getStudentById(Long id);
    ResponseStudentDTO createStudent(RequestStudentDTO studentDTO);
    ResponseStudentDTO updateStudent(Long id, RequestStudentDTO studentDTO);
    void deleteStudent(Long id);
} 