package com.students.repository;

import com.students.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryStudentRepository implements StudentRepository {
    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    @Override
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(idGenerator.getAndIncrement());
        } else {
            students.removeIf(s -> s.getId().equals(student.getId()));
        }
        students.add(student);
        return student;
    }

    @Override
    public void deleteById(Long id) {
        students.removeIf(student -> student.getId().equals(id));
    }

    @Override
    public boolean existsById(Long id) {
        return students.stream().anyMatch(student -> student.getId().equals(id));
    }
} 