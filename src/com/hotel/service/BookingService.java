package com.hotel.service;

import com.hotel.exception.BookingNotFoundException;
import com.hotel.exception.RoomNotAvailableException;
import com.hotel.model.Booking;
import com.hotel.model.Customer;
import com.hotel.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles Customer management, Room Bookings, Check-in, Check-out, and Cancellations.
 *
 * COLLECTIONS: Uses ArrayList to store customers and booking history in order.
 */
public class BookingService {

    private final RoomService roomService;

    // ArrayLists for storing customers and bookings
    private final List<Customer> customers = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();

    // Counter to auto-generate unique booking IDs (B1001, B1002, etc.)
    private int nextBookingId = 1001;

    public BookingService(RoomService roomService) {
        this.roomService = roomService;
    }

    // ================= CUSTOMER MANAGEMENT =================

    public void registerCustomer(String id, String name, String phone) {
        for (Customer c : customers) {
            if (c.getId().equalsIgnoreCase(id)) {
                throw new IllegalArgumentException("A customer with ID " + id + " already exists.");
            }
        }
        if (phone.length() < 7) {
            throw new IllegalArgumentException("Please enter a valid phone number.");
        }
        customers.add(new Customer(id, name, phone));
    }

    public Customer findCustomer(String id) {
        for (Customer c : customers) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    // ================= BOOKING OPERATIONS =================

    /**
     * Books an available room for a registered customer.
     * Prevents double booking by checking room availability.
     */
    public Booking bookRoom(String customerId, int roomNumber,
                            String checkInDate, String checkOutDate, int nights)
            throws RoomNotAvailableException {

        Customer customer = findCustomer(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with ID '" + customerId + "' is not registered.");
        }

        Room room = roomService.getRoom(roomNumber);
        if (room == null) {
            throw new RoomNotAvailableException("Room #" + roomNumber + " does not exist.");
        }

        if (!room.isAvailable()) {
            throw new RoomNotAvailableException("Room #" + roomNumber + " is already occupied or booked!");
        }

        if (nights <= 0) {
            throw new IllegalArgumentException("Number of nights must be at least 1.");
        }

        // Prevent double booking: mark room as unavailable
        room.setAvailable(false);

        // Generate unique booking ID
        String bookingId = "B" + (nextBookingId++);

        Booking booking = new Booking(bookingId, customer, room, checkInDate, checkOutDate, nights);
        bookings.add(booking);
        customer.incrementBookings();

        return booking;
    }

    public Booking checkIn(String bookingId) throws BookingNotFoundException {
        Booking booking = findBooking(bookingId);

        if ("CHECKED_IN".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Guest has already checked in for this booking.");
        }
        if ("CHECKED_OUT".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("This booking is already completed and checked out.");
        }
        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Cannot check-in to a cancelled booking.");
        }

        booking.setStatus("CHECKED_IN");
        return booking;
    }

    public Booking checkOut(String bookingId) throws BookingNotFoundException {
        Booking booking = findBooking(bookingId);

        if ("CHECKED_OUT".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("This booking is already checked out.");
        }
        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Cannot checkout a cancelled booking.");
        }

        // Update booking status
        booking.setStatus("CHECKED_OUT");

        // Make the room available again for future guests
        booking.getRoom().setAvailable(true);

        return booking;
    }

    public Booking cancelBooking(String bookingId) throws BookingNotFoundException {
        Booking booking = findBooking(bookingId);

        if ("CHECKED_OUT".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Cannot cancel a completed (checked-out) booking.");
        }
        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Booking is already cancelled.");
        }

        // Free the room
        booking.getRoom().setAvailable(true);
        booking.setStatus("CANCELLED");

        return booking;
    }

    public Booking findBooking(String bookingId) throws BookingNotFoundException {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(bookingId)) {
                return b;
            }
        }
        throw new BookingNotFoundException("No booking found with ID: " + bookingId);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }
}
