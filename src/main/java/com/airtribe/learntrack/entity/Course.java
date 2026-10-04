package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.util.IdGenerator;

public class Course {

    private String id;
    private String courseName;
    private String description;
    private int durationInWeeks;
    private boolean active;

    public Course() {
        this.active = true;
    }

    public Course(String courseName, String description, int durationInWeeks) {
        this.id = IdGenerator.getNextCourseId();
        this.courseName = courseName;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
        this.active = true;
    }

    public Course(String courseName, int durationInWeeks) {
        this.id = IdGenerator.getNextCourseId();
        this.courseName = courseName;
        this.durationInWeeks = durationInWeeks;
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(int durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "\nId: " + getId()
                + "\nCourse Name: " + getCourseName()
                + "\nDescription: " + getDescription()
                + "\nDuration (weeks): " + getDurationInWeeks()
                + "\nActive: " + getActive();
    }
}
