package com.airtribe.learntrack.service;

import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(new StudentRepository());
    }

    @Test
    void deactivateStudent_setsActiveFalse() {
        String message = studentService.addStudent("A", "B", "a@b.com", "B1");
        String id = message.substring(message.lastIndexOf(' ') + 1);

        assertEquals("Student deactivated", studentService.deactivateStudent(id));
        assertFalse(studentService.getStudent(id).getActive());
    }

    @Test
    void activateStudent_reactivatesDeactivatedStudent() {
        String message = studentService.addStudent("A", "B", "a@b.com", "B1");
        String id = message.substring(message.lastIndexOf(' ') + 1);

        studentService.deactivateStudent(id);
        assertEquals("Student activated", studentService.activateStudent(id));
    }

    @Test
    void searchById_includesDeactivatedStudent_updateStillRejected() {
        String message = studentService.addStudent("A", "B", "a@b.com", "B1");
        String id = message.substring(message.lastIndexOf(' ') + 1);

        studentService.deactivateStudent(id);

        assertFalse(studentService.searchStudentById(id).getActive());
        assertThrows(
                InvalidInputException.class,
                () -> studentService.updateStudent(id, "X", "Y", "x@y.com", "B2")
        );
    }

    @Test
    void removeStudent_deletesRecord() {
        String message = studentService.addStudent("A", "B", "a@b.com", "B1");
        String id = message.substring(message.lastIndexOf(' ') + 1);

        assertEquals("Student removed from the system", studentService.removeStudent(id));
        assertThrows(EntityNotFoundException.class, () -> studentService.getStudent(id));
    }
}
