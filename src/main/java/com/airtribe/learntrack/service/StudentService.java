package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public String addStudent(String firstName, String lastName, String email, String batch) {
        firstName = InputValidator.requireNonBlank(firstName, "First name");
        lastName = InputValidator.requireNonBlank(lastName, "Last name");
        email = InputValidator.requireNonBlank(email, "Email");
        batch = InputValidator.requireNonBlank(batch, "Batch");

        Student student = new Student(firstName, lastName, email, batch);
        studentRepository.save(student);
        return "Student added with ID " + student.getId();
    }

    public String addStudent(String firstName, String lastName, String email) {
        firstName = InputValidator.requireNonBlank(firstName, "First name");
        lastName = InputValidator.requireNonBlank(lastName, "Last name");
        email = InputValidator.requireNonBlank(email, "Email");

        Student student = new Student(firstName, lastName, email);
        studentRepository.save(student);
        return "Student added with ID " + student.getId();
    }

    public List<Student> listStudents() {
        return getAllStudents();
    }

    public List<Student> getAllStudents() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }

    public Student getStudent(String studentId) {
        return studentRepository.findById(studentId);
    }

    public Student getActiveStudent(String studentId) {
        return studentRepository.findActiveStudentById(studentId);
    }

    /**
     * Lookup by ID for display; returns active and deactivated students.
     */
    public Student searchStudentById(String studentId) {
        return studentRepository.findById(studentId);
    }

    public String updateStudent(
            String studentId,
            String firstName,
            String lastName,
            String email,
            String batch
    ) {
        Student student = getActiveStudent(studentId);

        student.setFirstName(InputValidator.requireNonBlank(firstName, "First name"));
        student.setLastName(InputValidator.requireNonBlank(lastName, "Last name"));
        student.setEmail(InputValidator.requireNonBlank(email, "Email"));
        student.setBatch(InputValidator.requireNonBlank(batch, "Batch"));

        return "Student updated successfully";
    }

    public String deactivateStudent(String studentId) {
        Student student = getActiveStudent(studentId);
        student.setActive(false);
        return "Student deactivated";
    }

    public String activateStudent(String studentId) {
        Student student = studentRepository.findById(studentId);

        if (student.getActive()) {
            return "Student is already active";
        }

        student.setActive(true);
        return "Student activated";
    }

    /**
     * Permanently removes a student from the in-memory store (soft-delete is {@link #deactivateStudent}).
     */
    public String removeStudent(String studentId) {
        studentRepository.removeById(studentId);
        return "Student removed from the system";
    }
}
