package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.util.IdGenerator;

import java.time.LocalDate;

public class Enrollment {

    private String id;
    private String studentId;
    private String courseId;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;

    public Enrollment(Student student, Course course) {
        this.id = IdGenerator.getNextEnrollmentId();
        this.student = student;
        this.course = course;
        this.studentId = student.getId();
        this.courseId = course.getId();
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nId: " + getId()
                + "\nStudent ID: " + studentId
                + "\nStudent Name: " + student.getFullName()
                + "\nCourse ID: " + courseId
                + "\nCourse Name: " + course.getCourseName()
                + "\nEnrollment Date: " + getEnrollmentDate()
                + "\nStatus: " + getStatus();
    }
}
