package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.util.IdGenerator;

public class Student extends Person {

    private String batch;

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String email, String batch) {
        super(IdGenerator.getNextStudentId(), firstName, lastName, email);
        this.batch = batch;
    }

    public Student(String firstName, String lastName, String email) {
        super(IdGenerator.getNextStudentId(), firstName, lastName, email);
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public void getDisplayName() {
        System.out.println("Student Details");
        System.out.println("Name : " + getFullName());
        System.out.println("Email ID : " + getEmail());
        System.out.println("Batch : " + batch);
    }

    @Override
    public String toString() {
        return "\nId: " + getId()
                + "\nName: " + getFullName()
                + "\nEmail ID: " + getEmail()
                + "\nBatch: " + getBatch()
                + "\nActive: " + getActive();
    }
}
