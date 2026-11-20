package com.hotel.model;

/**
 * Base abstract class representing a general person in the hotel system.
 *
 * OOP Concept: ABSTRACTION
 * - We cannot create a direct "Person" object because it's too general.
 * - Subclasses like Customer must provide specific role details.
 *
 * OOP Concept: ENCAPSULATION
 * - All fields are private and accessed via getters and setters.
 */
public abstract class Person {

    private final String id;
    private String name;
    private String phone;

    public Person(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Every subclass must define its own role (ABSTRACTION)
    public abstract String getRole();

    // Common details representation (can be overridden by subclasses - POLYMORPHISM)
    public String getDetails() {
        return getRole() + " | ID: " + id + " | Name: " + name + " | Phone: " + phone;
    }
}
