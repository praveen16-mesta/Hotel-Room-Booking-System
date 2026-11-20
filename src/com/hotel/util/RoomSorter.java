package com.hotel.util;

import com.hotel.model.Room;
import java.util.List;

/**
 * DSA Requirement: Manual Implementation of Selection Sort.
 *
 * This class sorts a list of Room objects by their tariff (price per night)
 * in ascending order (lowest price first).
 *
 * Algorithm Explanation:
 * 1. Find the room with the lowest tariff in the unsorted portion of the list.
 * 2. Swap it with the room at the current starting position.
 * 3. Repeat for each position until the entire list is sorted.
 *
 * Time Complexity: O(n^2) - easy for college students to explain in viva.
 * Space Complexity: O(1) - in-place sorting.
 */
public class RoomSorter {

    public static void sortByTariffAscending(List<Room> list) {
        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            // Find the index of the minimum tariff room in remaining unsorted portion
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).getTariff() < list.get(minIndex).getTariff()) {
                    minIndex = j;
                }
            }

            // Swap the found minimum room with the room at position i
            if (minIndex != i) {
                Room temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }
}
