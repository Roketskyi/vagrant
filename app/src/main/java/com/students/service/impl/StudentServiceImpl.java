package com.students.service.impl;

import com.students.dto.RequestStudentDTO;
import com.students.dto.ResponseStudentDTO;
import com.students.model.Student;
import com.students.repository.StudentRepository;
import com.students.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<ResponseStudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseStudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }

    @Override
    public ResponseStudentDTO createStudent(RequestStudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setResponseDate(LocalDateTime.now());
        return mapToResponseDTO(studentRepository.save(student));
    }

    @Override
    public ResponseStudentDTO updateStudent(Long id, RequestStudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setResponseDate(LocalDateTime.now());
        
        return mapToResponseDTO(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    private ResponseStudentDTO mapToResponseDTO(Student student) {
        return new ResponseStudentDTO(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getResponseDate()
        );
    }
} 