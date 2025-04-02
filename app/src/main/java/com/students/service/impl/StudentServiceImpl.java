package com.students.service.impl;

import com.students.dto.RequestStudentDTO;
import com.students.dto.ResponseStudentDTO;
import com.students.exception.StudentNotFoundException;
import com.students.model.Student;
import com.students.repository.StudentRepository;
import com.students.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseStudentDTO> getAllStudents() {
        log.info("Fetching all students");
        List<ResponseStudentDTO> students = studentRepository.findAll().stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
        log.info("Found {} students", students.size());
        return students;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseStudentDTO getStudentById(Long id) {
        log.info("Fetching student with id: {}", id);
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        log.info("Found student: {}", student.getName());
        return convertToResponseDTO(student);
    }

    @Override
    @Transactional
    public ResponseStudentDTO createStudent(RequestStudentDTO requestDTO) {
        log.info("Creating new student with name: {}", requestDTO.getName());
        Student student = convertToEntity(requestDTO);
        Student savedStudent = studentRepository.save(student);
        log.info("Created student with id: {}", savedStudent.getId());
        return convertToResponseDTO(savedStudent);
    }

    @Override
    @Transactional
    public ResponseStudentDTO updateStudent(Long id, RequestStudentDTO requestDTO) {
        log.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        updateStudentFromDTO(student, requestDTO);
        Student updatedStudent = studentRepository.save(student);
        log.info("Updated student with id: {}", updatedStudent.getId());
        return convertToResponseDTO(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
        log.info("Deleted student with id: {}", id);
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