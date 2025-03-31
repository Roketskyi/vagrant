package com.students.repository;

import com.students.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    Student save(Student student);
    void deleteById(Long id);
    boolean existsById(Long id);
} 