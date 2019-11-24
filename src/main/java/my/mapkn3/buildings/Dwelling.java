package my.mapkn3.buildings;

import my.mapkn3.exceptions.FloorIndexOutOfBoundsException;
import my.mapkn3.exceptions.SpaceIndexOutOfBoundsException;

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
        checkFloorIndex(index);
        return floors[index];
    }

    public void setFloor(int index, DwellingFloor floor) {
        checkFloorIndex(index);
        floors[index] = floor;
    }

    public Flat getFlat(int index) {
        checkFlatIndex(index);
        int i;
        for (i = 0; floors[i].getCountFlats() <= index; i++) {
            index -= floors[i].getCountFlats();
        }
        return floors[i].getFlat(index);
    }

    public void setFlat(int index, Flat flat) {
        checkFlatIndex(index);
        int i;
        for (i = 0; floors[i].getCountFlats() < index; i++) {
            index -= floors[i].getCountFlats();
        }
        floors[i].setFlat(index, flat);
    }

    public void deleteFlat(int index) {
        checkFlatIndex(index);
        int i;
        for (i = 0; floors[i].getCountFlats() <= index; i++) {
            index -= floors[i].getCountFlats();
        }
        floors[i].deleteFlat(index);
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

    private void checkFloorIndex(int index) {
        if (index < 0 || index >= getCountFloors()) {
            throw new FloorIndexOutOfBoundsException();
        }
    }

    private void checkFlatIndex(int index) {
        if (index < 0 || index >= getCountFlats()) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }
}
