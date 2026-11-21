package com.hotel;

import com.hotel.exception.RoomNotAvailableException;
import com.hotel.model.Booking;
import com.hotel.model.Room;
import com.hotel.service.BookingService;
import com.hotel.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main Class: Simple, concise console UI for the Hotel Room Booking System.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final RoomService roomService = new RoomService();
    private static final BookingService bookingService = new BookingService(roomService);

    public static void main(String[] args) {
        seedInitialData();
        System.out.println("==================================================");
        System.out.println("       HOTEL ROOM BOOKING SYSTEM (JAVA)           ");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice (1-6): ");

            switch (choice) {
                case 1: addRoom(); break;
                case 2: viewRooms(); break;
                case 3: bookRoom(); break;
                case 4: checkOut(); break;
                case 5: cancelBooking(); break;
                case 6:
                    running = false;
                    System.out.println("\nThank you for using the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Enter a number between 1 and 6.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add Room");
        System.out.println("2. View Rooms (Sorted by Price)");
        System.out.println("3. Book a Room");
        System.out.println("4. Check-out & Bill");
        System.out.println("5. Cancel Booking");
        System.out.println("6. Exit");
    }

    private static void addRoom() {
        System.out.println("\n--- Add Room ---");
        int num = readInt("Room Number: ");
        System.out.print("Room Type (Single/Double/Deluxe/Suite): ");
        String type = sc.nextLine().trim();
        double tariff = readDouble("Tariff per night (Rs.): ");

        try {
            roomService.addRoom(num, type, tariff);
            System.out.println("Room #" + num + " added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewRooms() {
        System.out.println("\n--- Rooms Inventory (Sorted by Price - Selection Sort) ---");
        List<Room> rooms = roomService.getSortedRoomsByTariff();
        if (rooms.isEmpty()) {
            System.out.println("No rooms registered.");
            return;
        }
        for (Room r : rooms) {
            System.out.println(r.getDetails());
        }
    }

    private static void bookRoom() {
        System.out.println("\n--- Direct Room Booking ---");
        try {
            String name = readText("Guest Name: ");
            String phone = readText("Phone Number: ");

            // 1. Immediately validate room number upon entry
            int roomNum = readInt("Room Number to Book: ");
            Room room = roomService.getRoom(roomNum);
            if (room == null || !room.isAvailable()) {
                throw new RoomNotAvailableException("Room #" + roomNum + " is not available.");
            }

            // 2. Immediately validate check-in date upon entry
            String inDateStr = readText("Check-in Date (DD-MM-YYYY): ");
            LocalDate inDate = parseDate(inDateStr);

            // 3. Immediately validate check-out date upon entry
            String outDateStr = readText("Check-out Date (DD-MM-YYYY): ");
            LocalDate outDate = parseDate(outDateStr);

            if (!outDate.isAfter(inDate)) {
                throw new IllegalArgumentException("Check-out date (" + outDateStr + ") must be after check-in date (" + inDateStr + ").");
            }

            int nights = readInt("Total Nights: ");
            if (nights <= 0) {
                throw new IllegalArgumentException("Total nights must be at least 1.");
            }

            Booking b = bookingService.bookRoom(name, phone, roomNum, inDateStr, outDateStr, nights);
            System.out.println("\nBooking Confirmed! Booking ID: " + b.getBookingId());
            System.out.println(b.getSummary());

        } catch (RoomNotAvailableException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }

    private static LocalDate parseDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date '" + dateStr + "'. Please enter a valid date in DD-MM-YYYY format.");
        }
    }

    private static void checkOut() {
        String id = readText("\nEnter Booking ID for Check-out: ");
        try {
            Booking b = bookingService.checkOut(id);
            System.out.println("\nCheck-out successful! Room #" + b.getRoom().getRoomNumber() + " is now Available.");
            b.printInvoice();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelBooking() {
        String id = readText("\nEnter Booking ID to Cancel: ");
        try {
            Booking b = bookingService.cancelBooking(id);
            System.out.println("Booking " + id + " cancelled. Room #" + b.getRoom().getRoomNumber() + " is now Available.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void seedInitialData() {
        roomService.addRoom(101, "Single", 1500.0);
        roomService.addRoom(102, "Double", 2500.0);
        roomService.addRoom(201, "Deluxe", 4000.0);
        roomService.addRoom(301, "Suite", 6500.0);
        try {
            bookingService.bookRoom("Rahul Sharma", "9876543210", 101, "21-11-2026", "23-11-2026", 2);
        } catch (Exception ignored) {}
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}