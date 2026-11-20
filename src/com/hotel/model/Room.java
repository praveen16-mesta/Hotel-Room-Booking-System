package com.hotel.model;

/**
 * Represents a hotel room.
 *
 * OOP Concept: ENCAPSULATION
 * - Private fields with public getters and setters to control access.
 */
public class Room {

    private final int roomNumber;
    private final String roomType; // Single, Double, Deluxe, Suite
    private double tariff;         // Price per night
    private boolean isAvailable;   // true if ready to book, false if occupied

    public Room(int roomNumber, String roomType, double tariff) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.tariff = tariff;
        this.isAvailable = true; // rooms are available by default
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getTariff() {
        return tariff;
    }

    public void setTariff(double tariff) {
        this.tariff = tariff;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public String getStatus() {
        return isAvailable ? "Available" : "Occupied";
    }

    public String getDetails() {
        return "Room #" + roomNumber + " [" + roomType + "] - Rs." + tariff + "/night | Status: " + getStatus();
    }
}
