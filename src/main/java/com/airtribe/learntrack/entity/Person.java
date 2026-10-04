package com.airtribe.learntrack.entity;

public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;

    public Person() {
        this.active = true;
    }

    protected Person(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public void getDisplayName() {
        System.out.println("Person Details");
        System.out.println("Name : " + firstName + " " + lastName);
        System.out.println("Email ID : " + email);
    }
}
