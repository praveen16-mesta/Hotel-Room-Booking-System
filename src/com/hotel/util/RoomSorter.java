package com.hotel.util;

import com.hotel.model.Room;
import java.util.List;

/**
 * DSA: Manual Selection Sort implementation to sort rooms by tariff ascending.
 * Time Complexity: O(n^2) | Space Complexity: O(1)
 */
public class RoomSorter {

    public static void sortByTariffAscending(List<Room> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).getTariff() < list.get(minIndex).getTariff()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Room temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }
}
