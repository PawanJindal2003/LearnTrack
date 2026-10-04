package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {

    private final List<Enrollment> enrollments = new ArrayList<>();

    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public List<Enrollment> findAll() {
        return enrollments;
    }

    public Enrollment findById(String id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId().equals(id)) {
                return enrollment;
            }
        }

        throw new EntityNotFoundException("Enrollment with ID " + id + " not found");
    }

    public List<Enrollment> findByStudentId(String studentId) {
        List<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId().equals(studentId)) {
                result.add(enrollment);
            }
        }

        return result;
    }

    public boolean hasActiveEnrollment(String studentId, String courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId().equals(studentId)
                    && enrollment.getCourseId().equals(courseId)
                    && enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }
}
