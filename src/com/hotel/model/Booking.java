package com.hotel.model;

/**
 * Represents a room booking transaction made by a customer.
 * Handles the calculation of room charges, discounts, and GST.
 */
public class Booking {

    private final String bookingId;
    private final Customer customer;
    private final Room room;
    private final String checkInDate;
    private final String checkOutDate;
    private final int numberOfNights;
    private String status; // "BOOKED", "CHECKED_IN", "CHECKED_OUT", "CANCELLED"

    private double roomCharges;
    private double discount;
    private double gst;
    private double totalAmount;

    public Booking(String bookingId, Customer customer, Room room,
                   String checkInDate, String checkOutDate, int numberOfNights) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfNights = numberOfNights;
        this.status = "BOOKED";

        calculateBill();
    }

    /**
     * Calculates room charges, discount (5% if nights >= 3), and GST (12%).
     */
    public void calculateBill() {
        this.roomCharges = numberOfNights * room.getTariff();

        // 5% discount for long stays (3 nights or more)
        if (numberOfNights >= 3) {
            this.discount = roomCharges * 0.05;
        } else {
            this.discount = 0.0;
        }

        double subTotal = roomCharges - discount;

        // 12% GST on hotel room tariff
        this.gst = subTotal * 0.12;

        this.totalAmount = subTotal + gst;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRoomCharges() {
        return roomCharges;
    }

    public double getDiscount() {
        return discount;
    }

    public double getGst() {
        return gst;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getSummary() {
        return "Booking ID: " + bookingId + " | Room #" + room.getRoomNumber()
                + " (" + room.getRoomType() + ") | Guest: " + customer.getName()
                + " | Nights: " + numberOfNights + " | Status: " + status
                + " | Total: Rs." + String.format("%.2f", totalAmount);
    }

    public void printInvoice() {
        System.out.println("--------------------------------------------------");
        System.out.println("                   HOTEL INVOICE                  ");
        System.out.println("--------------------------------------------------");
        System.out.println("Booking ID      : " + bookingId);
        System.out.println("Guest Name      : " + customer.getName() + " (ID: " + customer.getId() + ")");
        System.out.println("Phone           : " + customer.getPhone());
        System.out.println("Room Number     : " + room.getRoomNumber() + " (" + room.getRoomType() + ")");
        System.out.println("Tariff / Night  : Rs." + String.format("%.2f", room.getTariff()));
        System.out.println("Check-in Date   : " + checkInDate);
        System.out.println("Check-out Date  : " + checkOutDate);
        System.out.println("Total Nights    : " + numberOfNights);
        System.out.println("Booking Status  : " + status);
        System.out.println("--------------------------------------------------");
        System.out.println("Room Charges    : Rs." + String.format("%.2f", roomCharges));
        if (discount > 0) {
            System.out.println("Discount (5%)   : -Rs." + String.format("%.2f", discount));
        } else {
            System.out.println("Discount        : Rs.0.00");
        }
        System.out.println("GST (12%)       : +Rs." + String.format("%.2f", gst));
        System.out.println("--------------------------------------------------");
        System.out.println("Grand Total     : Rs." + String.format("%.2f", totalAmount));
        System.out.println("--------------------------------------------------");
    }
}
