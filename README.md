# Hotel Room Booking System (Core Java)

A simple, console-based Hotel Room Booking System written in Core Java.

---

## 1. How the Program Works

The system operates as a menu-driven console application:

1. **Startup**: The application pre-loads sample rooms (Single, Double, Deluxe, Suite) and registered customers so you can test immediately.
2. **Room Management**: The manager can view all rooms, search by room number, update tariffs, or add new rooms.
3. **Customer Registration**: New guests are registered with a unique Customer ID, name, and phone number.
4. **Room Booking**:
   - The user selects a customer and an available room.
   - Enter check-in date, check-out date, and total number of nights.
   - A unique Booking ID (e.g. `B1001`) is generated.
   - The room status automatically changes from **Available** to **Occupied** to prevent double-booking.
5. **Check-in & Check-out**:
   - Check-in confirms guest arrival using the Booking ID.
   - On Check-out, the system calculates room charges, applies a 5% discount for stays of 3+ nights, adds 12% GST, prints a clear printed invoice, and frees the room back to **Available**.
6. **Reports & Sorting**:
   - Check live occupancy percentage and total collected revenue.
   - Sort rooms by price per night using an in-place **Selection Sort** algorithm.

---

## 2. Main Features

- **Room Management**: Add rooms, view all rooms, view available rooms, search room by number, and update tariff.
- **Customer Management**: Register new customers and view details.
- **Room Booking**: Book available rooms with validation to prevent double-booking.
- **Check-in & Check-out**: Manage stay lifecycle and automatically release rooms upon departure.
- **Tariff & Invoice Calculation**:
  - `Room Charges = Nights * Tariff`
  - `Discount = 5%` (if stay >= 3 nights)
  - `GST = 12%` on room charges
  - `Grand Total = (Charges - Discount) + GST`
- **Cancellation**: Cancel active bookings and instantly restore room availability.
- **Reports**:
  - **Occupancy Report**: Total rooms, available rooms, occupied rooms, and occupancy percentage.
  - **Revenue Report**: Total bookings count, active bookings, completed stays, and total revenue earned.
- **DSA Sorting**: Sort rooms by price (lowest to highest) using manual Selection Sort.

---

## 3. How to Run the Program

### In VS Code:
1. Open the `HotelRoomBookingSystem` folder in VS Code.
2. Open `src/com/hotel/Main.java`.
3. Click the **Run** button at the top right (or press `F5`).

### In Terminal / Command Prompt:
```cmd
javac -d bin src/com/hotel/Main.java src/com/hotel/model/*.java src/com/hotel/service/*.java src/com/hotel/exception/*.java src/com/hotel/util/*.java
java -cp bin com.hotel.Main
```

---

## 4. Sample Output

### Main Menu
```
**************************************************
   WELCOME TO HOTEL ROOM BOOKING SYSTEM (JAVA)    
**************************************************

========== HOTEL ROOM BOOKING SYSTEM ==========
1.  Add Room
2.  View All Rooms
3.  Search Room
4.  Register Customer
5.  Book a Room
6.  View Bookings
7.  Check-in
8.  Check-out
9.  Cancel Booking
10. View Available Rooms
11. Occupancy Report
12. Revenue Report
13. Exit
===============================================
Enter your choice (1-13): 5
```

### Booking a Room
```
--- Book a Room ---
Available Rooms:
 - Room #101 [Single] - Rs.1500.0/night | Status: Available
 - Room #102 [Double] - Rs.2500.0/night | Status: Available
 - Room #201 [Deluxe] - Rs.4000.0/night | Status: Available
 - Room #301 [Suite] - Rs.6500.0/night | Status: Available

Enter Customer ID: C101
Enter Room Number to Book: 101
Enter Check-in Date (DD-MM-YYYY): 21-11-2026
Enter Check-out Date (DD-MM-YYYY): 24-11-2026
Enter Total Nights: 3

Booking Confirmed!
Booking ID: B1001 | Room #101 (Single) | Guest: Rahul Sharma | Nights: 3 | Status: BOOKED | Total: Rs.4788.00
Please remember your Booking ID: B1001
```

### Checkout & Generated Invoice
```
--- Guest Check-out & Billing ---
Enter Booking ID: B1001

Check-out completed! Room #101 is now Available.
--------------------------------------------------
                   HOTEL INVOICE                  
--------------------------------------------------
Booking ID      : B1001
Guest Name      : Rahul Sharma (ID: C101)
Phone           : 9876543210
Room Number     : 101 (Single)
Tariff / Night  : Rs.1500.00
Check-in Date   : 21-11-2026
Check-out Date  : 24-11-2026
Total Nights    : 3
Booking Status  : CHECKED_OUT
--------------------------------------------------
Room Charges    : Rs.4500.00
Discount (5%)   : -Rs.225.00
GST (12%)       : +Rs.513.00
--------------------------------------------------
Grand Total     : Rs.4788.00
--------------------------------------------------
```

### Occupancy Report
```
========== HOTEL OCCUPANCY REPORT ==========
Total Rooms in Hotel : 4
Available Rooms      : 4
Occupied Rooms       : 0
Occupancy Percentage : 0.00%
============================================
```

### Revenue Report
```
=========== HOTEL REVENUE REPORT ===========
Total Bookings Registered : 1
Completed (Paid) Stays    : 1
Active / Ongoing Bookings : 0
Cancelled Bookings        : 0
Total Realized Revenue    : Rs.4788.00
============================================
```
