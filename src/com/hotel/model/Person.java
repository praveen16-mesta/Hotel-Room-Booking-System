package com.hotel.model;

/**
 * Base abstract class representing a general Person in the system.
 * Demonstrates Abstraction and Encapsulation.
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

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }

    public abstract String getRole();

    public String getDetails() {
        return getRole() + " | ID: " + id + " | Name: " + name + " | Phone: " + phone;
    }
}
