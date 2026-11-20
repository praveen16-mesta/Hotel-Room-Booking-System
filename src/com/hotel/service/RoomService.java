package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.util.RoomSorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles all Room-related business logic and storage.
 *
 * COLLECTIONS: Uses HashMap for O(1) instant room lookup by room number.
 */
public class RoomService {

    // HashMap stores roomNumber as Key and Room object as Value
    private final Map<Integer, Room> rooms = new HashMap<>();

    public void addRoom(int roomNumber, String roomType, double tariff) {
        if (rooms.containsKey(roomNumber)) {
            throw new IllegalArgumentException("Room #" + roomNumber + " already exists.");
        }
        if (tariff <= 0) {
            throw new IllegalArgumentException("Room tariff must be greater than zero.");
        }
        rooms.put(roomNumber, new Room(roomNumber, roomType, tariff));
    }

    public Room getRoom(int roomNumber) {
        return rooms.get(roomNumber);
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    public List<Room> getSortedRoomsByTariff() {
        List<Room> list = new ArrayList<>(rooms.values());
        RoomSorter.sortByTariffAscending(list);
        return list;
    }

    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room r : rooms.values()) {
            if (r.isAvailable()) {
                available.add(r);
            }
        }
        return available;
    }

    public List<Room> getOccupiedRooms() {
        List<Room> occupied = new ArrayList<>();
        for (Room r : rooms.values()) {
            if (!r.isAvailable()) {
                occupied.add(r);
            }
        }
        return occupied;
    }

    public boolean updateRoomTariff(int roomNumber, double newTariff) {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            return false;
        }
        if (newTariff <= 0) {
            throw new IllegalArgumentException("New tariff must be positive.");
        }
        room.setTariff(newTariff);
        return true;
    }

    public boolean deleteRoom(int roomNumber) {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            return false;
        }
        if (!room.isAvailable()) {
            throw new IllegalStateException("Cannot delete Room #" + roomNumber + " because it is currently occupied.");
        }
        rooms.remove(roomNumber);
        return true;
    }

    public int getTotalRoomsCount() {
        return rooms.size();
    }
}
