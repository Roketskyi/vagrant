package com.students.service.impl;

import com.students.dto.RequestStudentDTO;
import com.students.dto.ResponseStudentDTO;
import com.students.model.Student;
import com.students.repository.StudentRepository;
import com.students.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseStudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseStudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        return convertToResponseDTO(student);
    }

    @Override
    @Transactional
    public ResponseStudentDTO createStudent(RequestStudentDTO requestDTO) {
        Student student = convertToEntity(requestDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToResponseDTO(savedStudent);
    }

    @Override
    @Transactional
    public ResponseStudentDTO updateStudent(Long id, RequestStudentDTO requestDTO) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        updateStudentFromDTO(student, requestDTO);
        Student updatedStudent = studentRepository.save(student);
        return convertToResponseDTO(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    private Student convertToEntity(RequestStudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setGrade(dto.getGrade());
        return student;
    }

    private void updateStudentFromDTO(Student student, RequestStudentDTO dto) {
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setGrade(dto.getGrade());
    }

    private ResponseStudentDTO convertToResponseDTO(Student student) {
        ResponseStudentDTO dto = new ResponseStudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        dto.setGrade(student.getGrade());
        return dto;
    }
} 