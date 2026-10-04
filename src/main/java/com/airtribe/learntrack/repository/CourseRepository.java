package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    private final List<Course> courses = new ArrayList<>();

    public void save(Course course) {
        courses.add(course);
    }

    public List<Course> findAll() {
        return courses;
    }

    public Course findById(String id) {
        for (Course course : courses) {
            if (course.getId().equals(id)) {
                return course;
            }
        }

        throw new EntityNotFoundException(
                "Course with ID " + id + " not found"
        );
    }

    public Course findActiveCourseById(String id) {
        for (Course course : courses) {
            if (course.getId().equals(id) && course.getActive()) {
                return course;
            }
        }

        throw new EntityNotFoundException(
                "Course with ID " + id + " not found"
        );
    }
}