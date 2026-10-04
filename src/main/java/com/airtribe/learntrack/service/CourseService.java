package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public String addCourse(String courseName, String description, int durationInWeeks) {
        courseName = InputValidator.requireNonBlank(courseName, "Course name");
        description = InputValidator.requireNonBlank(description, "Description");
        durationInWeeks = InputValidator.requirePositive(durationInWeeks, "Duration in weeks");

        Course course = new Course(courseName, description, durationInWeeks);
        courseRepository.save(course);
        return "Course added with ID " + course.getId();
    }

    public String addCourse(String courseName, int durationInWeeks) {
        courseName = InputValidator.requireNonBlank(courseName, "Course name");
        durationInWeeks = InputValidator.requirePositive(durationInWeeks, "Duration in weeks");

        Course course = new Course(courseName, durationInWeeks);
        courseRepository.save(course);
        return "Course added with ID " + course.getId();
    }

    public Course getCourse(String courseId) {
        return courseRepository.findById(courseId);
    }

    public Course getActiveCourse(String courseId) {
        return courseRepository.findActiveCourseById(courseId);
    }

    public List<Course> getAllCourses() {
        List<Course> activeCourses = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            if (course.getActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }

    public String activateCourse(String courseId) {
        Course course = getCourse(courseId);

        if (course.getActive()) {
            return "Course already active";
        }

        course.setActive(true);
        return "Course activated";
    }

    public String deactivateCourse(String courseId) {
        Course course = getCourse(courseId);

        if (!course.getActive()) {
            return "Course already de-activated";
        }

        course.setActive(false);
        return "Course de-activated";
    }
}
