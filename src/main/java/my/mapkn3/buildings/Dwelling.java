package my.mapkn3.buildings;

import java.util.Arrays;
import java.util.Comparator;

public class Dwelling {
    private DwellingFloor[] floors;

    public Dwelling(int countFloors, int[] countFlatsOnFloorArray) {
        this.floors = new DwellingFloor[countFloors];
        for (int i = 0; i < countFloors; i++) {
            this.floors[i] = new DwellingFloor(countFlatsOnFloorArray[i]);
        }
    }

    public Dwelling(DwellingFloor[] floors) {
        this.floors = floors;
    }

    public int getCountFloors() {
        return floors.length;
    }

    public int getCountFlats() {
        return Arrays.stream(floors)
                .mapToInt(DwellingFloor::getCountFlats)
                .sum();
    }

    public double getTotalSquare() {
        return Arrays
                .stream(floors)
                .mapToDouble(DwellingFloor::getTotalSquare)
                .sum();
    }

    public int getTotalCountRooms() {
        return Arrays.stream(floors)
                .mapToInt(DwellingFloor::getTotalCountRooms)
                .sum();
    }

    public DwellingFloor[] getFloors() {
        return floors;
    }

    public DwellingFloor getFloor(int index) {
        DwellingFloor floor = null;
        if (index >= 0 && index < floors.length) {
            floor = floors[index];
        }
        return floor;
    }

    public void setFloor(int index, DwellingFloor floor) {
        if (index >= 0 && index < floors.length) {
            floors[index] = floor;
        }
    }

    public Flat getFlat(int index) {
        Flat flat = null;
        if (index >= 0 && index < getCountFlats()) {
            int i;
            for (i = 0; floors[i].getCountFlats() <= index; i++) {
                index -= floors[i].getCountFlats();
            }
            flat = floors[i].getFlat(index);
        }
        return flat;
    }

    public void setFlat(int index, Flat flat) {
        if (index >= 0 && index < getCountFlats()) {
            int i;
            for (i = 0; floors[i].getCountFlats() <= index; i++) {
                index -= floors[i].getCountFlats();
            }
            floors[i].setFlat(index, flat);
        }
    }

    public void deleteFlat(int index) {
        if (index >= 0 && index < getCountFlats()) {
            int i;
            for (i = 0; floors[i].getCountFlats() <= index; i++) {
                index -= floors[i].getCountFlats();
            }
            floors[i].deleteFlat(index);
        }
    }

    public Flat getBestSpace() {
        return Arrays.stream(floors)
                .map(DwellingFloor::getBestSpace)
                .max(Comparator.comparingDouble(Flat::getSquare))
                .orElse(null);
    }

    public Flat[] getSortedFlatDesc() {
        return Arrays.stream(floors)
                .map(DwellingFloor::getFlats)
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparingDouble(Flat::getSquare))
                .toArray(Flat[]::new);
    }
}
