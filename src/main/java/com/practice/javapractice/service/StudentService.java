package com.practice.javapractice.service;

import com.practice.javapractice.model.Student;
import com.practice.javapractice.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        boolean isDuplicate = studentRepository.existsByNameAndDepartment(student.getName(), student.getDepartment());

        if (isDuplicate) {
            throw new RuntimeException("Student with the same name and department already exists");
        }
        return studentRepository.save(student);
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student existingStudent = studentRepository.findById(id);
        if (existingStudent != null) {
            // Update name if name is passed
            if (studentDetails.getName() != null && !studentDetails.getName().isEmpty()) {
                existingStudent.setName(studentDetails.getName());
            }
            // Update department if department is passed
            if (studentDetails.getDepartment() != null && studentDetails.getDepartment().isEmpty()) {
                existingStudent.setDepartment(studentDetails.getDepartment());
            }
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    public boolean deleteStudentById(Long id) {
        return studentRepository.deleteById(id);
    }
}
