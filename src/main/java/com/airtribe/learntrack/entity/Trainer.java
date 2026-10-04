package com.airtribe.learntrack.entity;

import com.airtribe.learntrack.util.IdGenerator;

public class Trainer extends Person {

    private String subject;
    private int experience;

    public Trainer(String firstName, String lastName, String email, String subject, int experience) {
        super(IdGenerator.getNextTrainerId(), firstName, lastName, email);
        this.subject = subject;
        this.experience = experience;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public void getDisplayName() {
        System.out.println("Trainer Details");
        System.out.println("Name : " + getFullName());
        System.out.println("Email ID : " + getEmail());
        System.out.println("Subject : " + subject);
        System.out.println("Year of Experience : " + experience);
    }

    @Override
    public String toString() {
        return "\nId: " + getId()
                + "\nName: " + getFullName()
                + "\nEmail ID: " + getEmail()
                + "\nSubject: " + getSubject()
                + "\nExperience: " + getExperience()
                + "\nActive: " + getActive();
    }
}
