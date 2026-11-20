package com.hotel;

import com.hotel.exception.BookingNotFoundException;
import com.hotel.exception.RoomNotAvailableException;
import com.hotel.model.Booking;
import com.hotel.model.Customer;
import com.hotel.model.Room;
import com.hotel.service.BookingService;
import com.hotel.service.ReportService;
import com.hotel.service.RoomService;

import java.util.List;
import java.util.Scanner;

/**
 * Main class: Console user interface for the Hotel Room Booking System.
 * Uses switch-case menu and simple Scanner input helpers.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final RoomService roomService = new RoomService();
    private static final BookingService bookingService = new BookingService(roomService);
    private static final ReportService reportService = new ReportService(roomService, bookingService);

    public static void main(String[] args) {
        // Pre-populate sample rooms and customers so the app is ready for testing immediately
        seedInitialData();

        System.out.println("**************************************************");
        System.out.println("   WELCOME TO HOTEL ROOM BOOKING SYSTEM (JAVA)    ");
        System.out.println("**************************************************");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice (1-13): ");

            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewAllRooms();
                    break;
                case 3:
                    searchRoom();
                    break;
                case 4:
                    registerCustomer();
                    break;
                case 5:
                    bookRoom();
                    break;
                case 6:
                    viewBookings();
                    break;
                case 7:
                    checkIn();
                    break;
                case 8:
                    checkOut();
                    break;
                case 9:
                    cancelBooking();
                    break;
                case 10:
                    viewAvailableRooms();
                    break;
                case 11:
                    reportService.displayOccupancyReport();
                    break;
                case 12:
                    reportService.displayRevenueReport();
                    break;
                case 13:
                    running = false;
                    System.out.println("\nThank you for using Hotel Room Booking System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 13.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========== HOTEL ROOM BOOKING SYSTEM ==========");
        System.out.println("1.  Add Room");
        System.out.println("2.  View All Rooms");
        System.out.println("3.  Search Room");
        System.out.println("4.  Register Customer");
        System.out.println("5.  Book a Room");
        System.out.println("6.  View Bookings");
        System.out.println("7.  Check-in");
        System.out.println("8.  Check-out");
        System.out.println("9.  Cancel Booking");
        System.out.println("10. View Available Rooms");
        System.out.println("11. Occupancy Report");
        System.out.println("12. Revenue Report");
        System.out.println("13. Exit");
        System.out.println("===============================================");
    }

    // 1. Add Room
    private static void addRoom() {
        System.out.println("\n--- Add New Room ---");
        int roomNumber = readInt("Enter Room Number (e.g. 105): ");
        System.out.println("Room Types: 1. Single | 2. Double | 3. Deluxe | 4. Suite");
        int typeChoice = readInt("Select Room Type (1-4): ");
        String roomType;
        switch (typeChoice) {
            case 1: roomType = "Single"; break;
            case 2: roomType = "Double"; break;
            case 3: roomType = "Deluxe"; break;
            case 4: roomType = "Suite"; break;
            default:
                System.out.println("Invalid type selected. Defaulting to 'Single'.");
                roomType = "Single";
        }

        double tariff = readDouble("Enter Tariff per Night (Rs.): ");

        try {
            roomService.addRoom(roomNumber, roomType, tariff);
            System.out.println("Success: Room #" + roomNumber + " added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // 2. View All Rooms (with optional sort by tariff)
    private static void viewAllRooms() {
        System.out.println("\n--- All Rooms ---");
        List<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms registered in the system.");
            return;
        }

        System.out.print("Do you want to sort rooms by price (lowest first)? (y/n): ");
        String sortChoice = sc.nextLine().trim();
        if ("y".equalsIgnoreCase(sortChoice)) {
            rooms = roomService.getSortedRoomsByTariff();
            System.out.println("(Sorted using Selection Sort algorithm)");
        }

        for (Room r : rooms) {
            System.out.println(r.getDetails());
        }
    }

    // 3. Search Room
    private static void searchRoom() {
        System.out.println("\n--- Search Room ---");
        int roomNumber = readInt("Enter Room Number to Search: ");
        Room room = roomService.getRoom(roomNumber);
        if (room != null) {
            System.out.println("Room Found: " + room.getDetails());
            System.out.println("Room Type: " + room.getRoomType() + " | Price: Rs." + room.getTariff() + "/night | Status: " + room.getStatus());

            System.out.print("Do you want to update price or delete room? (u = update, d = delete, n = cancel): ");
            String action = sc.nextLine().trim();
            if ("u".equalsIgnoreCase(action)) {
                double newTariff = readDouble("Enter new price per night: ");
                try {
                    roomService.updateRoomTariff(roomNumber, newTariff);
                    System.out.println("Room price updated successfully.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Update failed: " + e.getMessage());
                }
            } else if ("d".equalsIgnoreCase(action)) {
                try {
                    boolean deleted = roomService.deleteRoom(roomNumber);
                    if (deleted) {
                        System.out.println("Room #" + roomNumber + " deleted successfully.");
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Cannot delete: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No room found with number: " + roomNumber);
        }
    }

    // 4. Register Customer
    private static void registerCustomer() {
        System.out.println("\n--- Register Customer ---");
        String id = readText("Customer ID (e.g. C101): ");
        String name = readText("Customer Name: ");
        String phone = readText("Phone Number: ");

        try {
            bookingService.registerCustomer(id, name, phone);
            System.out.println("Customer '" + name + "' registered successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    // 5. Book a Room
    private static void bookRoom() {
        System.out.println("\n--- Book a Room ---");

        // Show available rooms
        List<Room> available = roomService.getAvailableRooms();
        if (available.isEmpty()) {
            System.out.println("Sorry, no rooms are currently available.");
            return;
        }

        System.out.println("Available Rooms:");
        for (Room r : available) {
            System.out.println(" - " + r.getDetails());
        }

        String customerId = readText("Enter Customer ID: ");
        Customer customer = bookingService.findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found! Please register the customer first (Option 4).");
            return;
        }

        int roomNumber = readInt("Enter Room Number to Book: ");
        String checkInDate = readText("Enter Check-in Date (DD-MM-YYYY): ");
        String checkOutDate = readText("Enter Check-out Date (DD-MM-YYYY): ");
        int nights = readInt("Enter Total Nights: ");

        try {
            Booking booking = bookingService.bookRoom(customerId, roomNumber, checkInDate, checkOutDate, nights);
            System.out.println("\nBooking Confirmed!");
            System.out.println(booking.getSummary());
            System.out.println("Please remember your Booking ID: " + booking.getBookingId());
        } catch (RoomNotAvailableException e) {
            System.out.println("Booking Failed (Room Unavailable): " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }

    // 6. View Bookings
    private static void viewBookings() {
        System.out.println("\n--- All Bookings / Booking History ---");
        List<Booking> list = bookingService.getAllBookings();
        if (list.isEmpty()) {
            System.out.println("No bookings recorded yet.");
            return;
        }
        for (Booking b : list) {
            System.out.println(b.getSummary());
        }
    }

    // 7. Check-in
    private static void checkIn() {
        System.out.println("\n--- Guest Check-in ---");
        String bookingId = readText("Enter Booking ID: ");

        try {
            Booking b = bookingService.checkIn(bookingId);
            System.out.println("Success: Check-in completed for Guest " + b.getCustomer().getName() + " in Room #" + b.getRoom().getRoomNumber());
        } catch (BookingNotFoundException e) {
            System.out.println("Check-in Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Check-in Error: " + e.getMessage());
        }
    }

    // 8. Check-out
    private static void checkOut() {
        System.out.println("\n--- Guest Check-out & Billing ---");
        String bookingId = readText("Enter Booking ID: ");

        try {
            Booking b = bookingService.checkOut(bookingId);
            System.out.println("\nCheck-out completed! Room #" + b.getRoom().getRoomNumber() + " is now Available.");
            b.printInvoice();
        } catch (BookingNotFoundException e) {
            System.out.println("Check-out Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Check-out Error: " + e.getMessage());
        }
    }

    // 9. Cancel Booking
    private static void cancelBooking() {
        System.out.println("\n--- Cancel Booking ---");
        String bookingId = readText("Enter Booking ID to cancel: ");

        try {
            Booking b = bookingService.cancelBooking(bookingId);
            System.out.println("Booking " + bookingId + " has been cancelled. Room #" + b.getRoom().getRoomNumber() + " is now Available.");
        } catch (BookingNotFoundException e) {
            System.out.println("Cancellation Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Cancellation Error: " + e.getMessage());
        }
    }

    // 10. View Available Rooms
    private static void viewAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        List<Room> available = roomService.getAvailableRooms();
        if (available.isEmpty()) {
            System.out.println("No rooms are currently available.");
            return;
        }
        for (Room r : available) {
            System.out.println(r.getDetails());
        }
    }

    // Helper: Seeds initial demo data
    private static void seedInitialData() {
        // Pre-add sample rooms
        roomService.addRoom(101, "Single", 1500.0);
        roomService.addRoom(102, "Double", 2500.0);
        roomService.addRoom(201, "Deluxe", 4000.0);
        roomService.addRoom(301, "Suite", 6500.0);

        // Pre-register sample customers
        bookingService.registerCustomer("C101", "Rahul Sharma", "9876543210");
        bookingService.registerCustomer("C102", "Priya Patel", "9123456780");
    }

    // Helper: Read single line of text safely
    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // Helper: Read integer safely with loop
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // Helper: Read double safely with loop
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric amount.");
            }
        }
    }
}
