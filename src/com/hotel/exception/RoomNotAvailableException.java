package com.hotel.exception;

/**
 * Custom Exception: thrown when a requested room is not available for booking
 * (e.g. already occupied or does not exist).
 */
public class RoomNotAvailableException extends Exception {

    public RoomNotAvailableException(String message) {
        super(message);
    }
}
