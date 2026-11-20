package com.hotel.model;

/**
 * Represents a customer who books rooms in the hotel.
 *
 * OOP Concept: INHERITANCE
 * - Customer extends Person, inheriting id, name, and phone.
 *
 * OOP Concept: POLYMORPHISM
 * - Overrides getRole() and getDetails() to add customer-specific data.
 */
public class Customer extends Person {

    private int totalBookings;

    public Customer(String id, String name, String phone) {
        super(id, name, phone);
        this.totalBookings = 0;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void incrementBookings() {
        this.totalBookings++;
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " | Total Bookings: " + totalBookings;
    }
}
