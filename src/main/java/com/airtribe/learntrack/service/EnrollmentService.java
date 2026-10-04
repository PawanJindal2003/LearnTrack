package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;

public class EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public String enrollStudent(String studentId, String courseId) {
        studentId = InputValidator.requireNonBlank(studentId, "Student ID");
        courseId = InputValidator.requireNonBlank(courseId, "Course ID");

        Student student = studentRepository.findActiveStudentById(studentId);
        Course course = courseRepository.findActiveCourseById(courseId);

        if (enrollmentRepository.hasActiveEnrollment(studentId, courseId)) {
            throw new InvalidInputException(
                    "Student is already actively enrolled in this course."
            );
        }

        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);
        return "Student enrolled with enrollment ID " + enrollment.getId();
    }

    public List<Enrollment> getStudentEnrollments(String studentId) {
        studentId = InputValidator.requireNonBlank(studentId, "Student ID");
        studentRepository.findById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    public String completeEnrollment(String enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId);

        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot complete a cancelled enrollment");
        }

        if (enrollment.getStatus() == EnrollmentStatus.COMPLETED) {
            return "Enrollment already completed";
        }

        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        return "Marked enrollment completed";
    }

    public String cancelEnrollment(String enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId);

        if (enrollment.getStatus() == EnrollmentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed enrollment");
        }

        if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
            return "Enrollment already cancelled";
        }

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        return "Marked enrollment cancelled";
    }
}
