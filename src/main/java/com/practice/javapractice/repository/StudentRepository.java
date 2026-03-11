package com.practice.javapractice.repository;

import com.practice.javapractice.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.yaml.snakeyaml.nodes.Tag.STR;

@Repository
public class StudentRepository {
    // In-memory DB
    private Map<Long, Student> studentDB = new HashMap<>();
    private Long currentId = 1L;

    // Create & Update
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(currentId++);
        }
        studentDB.put(student.getId(), student);
        return student;
    }

    // Read all
    public List<Student> findAll() {
        return new ArrayList<>(studentDB.values());
    }

    // Read by id
    public Student findById(Long id) {
        return studentDB.get(id);
    }

    // Delete by id
    public boolean deleteById(Long id) {
        if (studentDB.containsKey(id)) {
            studentDB.remove(id);
            return true;
        }
        return false;
    }

    public boolean existsByNameAndDepartment(String name, String department) {
        return studentDB.values().stream().anyMatch(student -> student.getName().equalsIgnoreCase(name) && student.getDepartment().equalsIgnoreCase(department));
    }
}
