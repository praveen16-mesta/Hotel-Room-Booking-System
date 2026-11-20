package com.hotel.service;

import com.hotel.model.Booking;

import java.util.List;

/**
 * Generates occupancy and revenue reports for hotel management.
 */
public class ReportService {

    private final RoomService roomService;
    private final BookingService bookingService;

    public ReportService(RoomService roomService, BookingService bookingService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    public void displayOccupancyReport() {
        int totalRooms = roomService.getTotalRoomsCount();
        int availableRooms = roomService.getAvailableRooms().size();
        int occupiedRooms = roomService.getOccupiedRooms().size();

        double occupancyRate = 0.0;
        if (totalRooms > 0) {
            occupancyRate = ((double) occupiedRooms / totalRooms) * 100.0;
        }

        System.out.println();
        System.out.println("========== HOTEL OCCUPANCY REPORT ==========");
        System.out.println("Total Rooms in Hotel : " + totalRooms);
        System.out.println("Available Rooms      : " + availableRooms);
        System.out.println("Occupied Rooms       : " + occupiedRooms);
        System.out.printf("Occupancy Percentage : %.2f%%\n", occupancyRate);
        System.out.println("============================================");
    }

    public void displayRevenueReport() {
        List<Booking> bookings = bookingService.getAllBookings();
        double totalRevenue = 0.0;
        int completedBookings = 0;
        int activeBookings = 0;
        int cancelledBookings = 0;

        for (Booking b : bookings) {
            if ("CHECKED_OUT".equalsIgnoreCase(b.getStatus())) {
                totalRevenue += b.getTotalAmount();
                completedBookings++;
            } else if ("CANCELLED".equalsIgnoreCase(b.getStatus())) {
                cancelledBookings++;
            } else {
                activeBookings++;
            }
        }

        System.out.println();
        System.out.println("=========== HOTEL REVENUE REPORT ===========");
        System.out.println("Total Bookings Registered : " + bookings.size());
        System.out.println("Completed (Paid) Stays    : " + completedBookings);
        System.out.println("Active / Ongoing Bookings : " + activeBookings);
        System.out.println("Cancelled Bookings        : " + cancelledBookings);
        System.out.printf("Total Realized Revenue    : Rs.%.2f\n", totalRevenue);
        System.out.println("============================================");
    }
}
