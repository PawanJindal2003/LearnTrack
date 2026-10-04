package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private final List<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public List<Student> findAll() {
        return students;
    }

    public Student findById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }

        throw new EntityNotFoundException("Student with ID " + id + " not found");
    }

    public Student findActiveStudentById(String id) {
        Student student = findById(id);
        if (!student.getActive()) {
            throw new InvalidInputException(
                    "Student with ID " + id + " is deactivated. Activate the student before using this operation."
            );
        }
        return student;
    }

    public void removeById(String id) {
        Student student = findById(id);
        students.remove(student);
    }
}
