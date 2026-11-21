package com.hotel.model;

/**
 * Represents a room booking transaction with billing details.
 */
public class Booking {

    private final String bookingId;
    private final Customer customer;
    private final Room room;
    private final String checkInDate;
    private final String checkOutDate;
    private final int numberOfNights;
    private String status;

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

    public void calculateBill() {
        this.roomCharges = numberOfNights * room.getTariff();
        this.discount = (numberOfNights >= 3) ? (roomCharges * 0.05) : 0.0;
        double subTotal = roomCharges - discount;
        this.gst = subTotal * 0.12; // 12% GST
        this.totalAmount = subTotal + gst;
    }

    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }

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
        System.out.println("Booking ID     : " + bookingId);
        System.out.println("Guest Name     : " + customer.getName() + " (ID: " + customer.getId() + ")");
        System.out.println("Room Number    : #" + room.getRoomNumber() + " (" + room.getRoomType() + ")");
        System.out.println("Stay Duration  : " + numberOfNights + " night(s) (" + checkInDate + " to " + checkOutDate + ")");
        System.out.println("Status         : " + status);
        System.out.println("--------------------------------------------------");
        System.out.printf("Room Charges   : Rs.%.2f\n", roomCharges);
        System.out.printf("Discount (5%%)  : -Rs.%.2f\n", discount);
        System.out.printf("GST (12%%)      : +Rs.%.2f\n", gst);
        System.out.println("--------------------------------------------------");
        System.out.printf("Grand Total    : Rs.%.2f\n", totalAmount);
        System.out.println("--------------------------------------------------");
    }
}
