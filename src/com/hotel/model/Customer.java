package com.hotel.model;

/**
 * Customer class extending Person (Inheritance & Polymorphism).
 */
public class Customer extends Person {

    private int totalBookings;

    public Customer(String id, String name, String phone) {
        super(id, name, phone);
        this.totalBookings = 0;
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
        return super.getDetails() + " | Bookings: " + totalBookings;
    }
}
