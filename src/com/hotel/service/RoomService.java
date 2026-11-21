package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.util.RoomSorter;

import java.util.ArrayList;

/**
 * Handles room inventory using a simple ArrayList.
 */
public class RoomService {

    // ArrayList to store all rooms
    private ArrayList<Room> rooms = new ArrayList<>();

    public void addRoom(int roomNumber, String roomType, double tariff) {
        // Check if room number already exists
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                throw new IllegalArgumentException("Room #" + roomNumber + " already exists.");
            }
        }
        rooms.add(new Room(roomNumber, roomType, tariff));
    }

    public Room getRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) return r;
        }
        return null;
    }

    public ArrayList<Room> getAllRooms() {
        return rooms;
    }

    public ArrayList<Room> getSortedRoomsByTariff() {
        ArrayList<Room> sorted = new ArrayList<>(rooms);
        RoomSorter.sortByTariffAscending(sorted);
        return sorted;
    }
}
