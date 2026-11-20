package com.hotel.exception;

/**
 * Custom Exception: thrown when an operation refers to a booking ID that does not exist.
 */
public class BookingNotFoundException extends Exception {

    public BookingNotFoundException(String message) {
        super(message);
    }
}
