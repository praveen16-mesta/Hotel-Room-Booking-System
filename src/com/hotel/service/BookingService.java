package com.hotel.service;

import com.hotel.exception.BookingNotFoundException;
import com.hotel.exception.RoomNotAvailableException;
import com.hotel.model.Booking;
import com.hotel.model.Customer;
import com.hotel.model.Room;

import java.util.ArrayList;

/**
 * Manages room bookings, check-out, and cancellations using a simple ArrayList.
 */
public class BookingService {

    private final RoomService roomService;

    // ArrayList to store all bookings
    private ArrayList<Booking> bookings = new ArrayList<>();

    private int nextBookingId = 1001;
    private int nextCustomerId = 101;

    public BookingService(RoomService roomService) {
        this.roomService = roomService;
    }

    // Book a room directly with guest details
    public Booking bookRoom(String guestName, String phone, int roomNumber,
                            String checkIn, String checkOut, int nights)
            throws RoomNotAvailableException {

        Room room = roomService.getRoom(roomNumber);
        if (room == null || !room.isAvailable()) {
            throw new RoomNotAvailableException("Room #" + roomNumber + " is not available.");
        }

        // Create Customer (shows OOP Inheritance)
        Customer customer = new Customer("C" + (nextCustomerId++), guestName, phone);

        room.setAvailable(false);
        room.setBookedBy(guestName);

        String bookingId = "B" + (nextBookingId++);
        Booking booking = new Booking(bookingId, customer, room, checkIn, checkOut, nights);
        bookings.add(booking);
        return booking;
    }

    // Check out and free the room
    public Booking checkOut(String bookingId) throws BookingNotFoundException {
        Booking b = findBooking(bookingId);
        if ("CHECKED_OUT".equalsIgnoreCase(b.getStatus())) {
            throw new IllegalStateException("Booking already checked out.");
        }
        b.setStatus("CHECKED_OUT");
        b.getRoom().setAvailable(true);
        return b;
    }

    // Cancel booking and free the room
    public Booking cancelBooking(String bookingId) throws BookingNotFoundException {
        Booking b = findBooking(bookingId);
        if ("CHECKED_OUT".equalsIgnoreCase(b.getStatus())) {
            throw new IllegalStateException("Cannot cancel a completed booking.");
        }
        b.setStatus("CANCELLED");
        b.getRoom().setAvailable(true);
        return b;
    }

    public Booking findBooking(String bookingId) throws BookingNotFoundException {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(bookingId)) return b;
        }
        throw new BookingNotFoundException("No booking found with ID: " + bookingId);
    }

    public ArrayList<Booking> getAllBookings() {
        return bookings;
    }
}
