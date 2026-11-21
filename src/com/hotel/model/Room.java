package com.hotel.model;

/**
 * Room class representing a hotel room.
 * Tracks availability and the guest who booked it.
 */
public class Room {

    private final int roomNumber;
    private final String roomType;
    private double tariff;
    private boolean isAvailable;
    private String bookedBy;

    public Room(int roomNumber, String roomType, double tariff) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.tariff = tariff;
        this.isAvailable = true;
        this.bookedBy = "";
    }

    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public double getTariff() { return tariff; }
    public boolean isAvailable() { return isAvailable; }

    public void setTariff(double tariff) { this.tariff = tariff; }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
        if (available) this.bookedBy = "";
    }

    public String getBookedBy() { return bookedBy; }
    public void setBookedBy(String bookedBy) { this.bookedBy = bookedBy; }

    public String getDetails() {
        String status = isAvailable ? "Available" : "Booked by " + bookedBy;
        return "Room #" + roomNumber + " [" + roomType + "] - Rs." + tariff + "/night | Status: " + status;
    }
}
