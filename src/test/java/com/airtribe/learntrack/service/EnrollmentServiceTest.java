package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnrollmentServiceTest {

    private EnrollmentService enrollmentService;
    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new StudentRepository();
        courseRepository = new CourseRepository();
        enrollmentRepository = new EnrollmentRepository();
        enrollmentService = new EnrollmentService(
                studentRepository,
                courseRepository,
                enrollmentRepository
        );
    }

    @Test
    void completeEnrollment_rejectsCancelledEnrollment() {
        Enrollment enrollment = saveEnrollment();
        enrollment.setStatus(EnrollmentStatus.CANCELLED);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> enrollmentService.completeEnrollment(enrollment.getId())
        );
        assertEquals("Cannot complete a cancelled enrollment", ex.getMessage());
    }

    @Test
    void cancelEnrollment_rejectsCompletedEnrollment() {
        Enrollment enrollment = saveEnrollment();
        enrollment.setStatus(EnrollmentStatus.COMPLETED);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> enrollmentService.cancelEnrollment(enrollment.getId())
        );
        assertEquals("Cannot cancel a completed enrollment", ex.getMessage());
    }

    @Test
    void completeEnrollment_succeedsFromActive() {
        Enrollment enrollment = saveEnrollment();

        String message = enrollmentService.completeEnrollment(enrollment.getId());

        assertEquals("Marked enrollment completed", message);
        assertEquals(EnrollmentStatus.COMPLETED, enrollment.getStatus());
    }

    @Test
    void cancelEnrollment_succeedsFromActive() {
        Enrollment enrollment = saveEnrollment();

        String message = enrollmentService.cancelEnrollment(enrollment.getId());

        assertEquals("Marked enrollment cancelled", message);
        assertEquals(EnrollmentStatus.CANCELLED, enrollment.getStatus());
    }

    @Test
    void enrollStudent_rejectsDuplicateActiveEnrollment() {
        Student student = new Student("Jane", "Doe", "jane@example.com", "B1");
        Course course = new Course("Java Basics", 4);
        studentRepository.save(student);
        courseRepository.save(course);

        enrollmentService.enrollStudent(student.getId(), course.getId());

        InvalidInputException ex = assertThrows(
                InvalidInputException.class,
                () -> enrollmentService.enrollStudent(student.getId(), course.getId())
        );
        assertEquals("Student is already actively enrolled in this course.", ex.getMessage());
    }

    private Enrollment saveEnrollment() {
        Student student = new Student("Jane", "Doe", "jane@example.com", "B1");
        Course course = new Course("Java Basics", 4);
        studentRepository.save(student);
        courseRepository.save(course);
        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);
        return enrollment;
    }
}
