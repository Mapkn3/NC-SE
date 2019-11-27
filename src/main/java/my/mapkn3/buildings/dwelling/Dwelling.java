package my.mapkn3.buildings.dwelling;

import my.mapkn3.buildings.iterators.BuildingIterator;
import my.mapkn3.exceptions.FloorIndexOutOfBoundsException;
import my.mapkn3.exceptions.SpaceIndexOutOfBoundsException;
import my.mapkn3.buildings.interfaces.Building;
import my.mapkn3.buildings.interfaces.Floor;
import my.mapkn3.buildings.interfaces.Space;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class Dwelling implements Building {
    private Floor[] floors;

    public Dwelling(int countFloors, int[] countSpacesOnFloorArray) {
        this.floors = new Floor[countFloors];
        for (int i = 0; i < countFloors; i++) {
            this.floors[i] = new DwellingFloor(countSpacesOnFloorArray[i]);
        }
    }

    public Dwelling(Floor[] floors) {
        this.floors = floors;
    }

    public int getCountFloors() {
        return floors.length;
    }

    @Override
    public int getCountSpaces() {
        return Arrays.stream(floors)
                .mapToInt(Floor::getCountSpace)
                .sum();
    }

    @Override
    public double getTotalSquare() {
        return Arrays.stream(floors)
                .mapToDouble(Floor::getTotalSquare)
                .sum();
    }

    @Override
    public int getTotalCountRooms() {
        return Arrays.stream(floors)
                .mapToInt(Floor::getTotalCountRooms)
                .sum();
    }

    @Override
    public Floor[] getFloorArray() {
        return floors;
    }

    @Override
    public Floor getFloor(int index) {
        checkFloorIndex(index);
        return floors[index];
    }

    @Override
    public void setFloor(int index, Floor floor) {
        checkFloorIndex(index);
        floors[index] = floor;
    }

    @Override
    public Space getSpace(int index) {
        checkFlatIndex(index);
        int i;
        for (i = 0; floors[i].getCountSpace() <= index; i++) {
            index -= floors[i].getCountSpace();
        }
        return floors[i].getSpace(index);
    }

    @Override
    public void setSpace(int index, Space space) {
        checkFlatIndex(index);
        int i;
        for (i = 0; floors[i].getCountSpace() <= index; i++) {
            index -= floors[i].getCountSpace();
        }
        floors[i].setSpace(index, space);
    }

    @Override
    public void insertSpace(int index, Space space) {
        checkFlatIndex(index);
        int i;
        for (i = 0; floors[i].getCountSpace() <= index; i++) {
            index -= floors[i].getCountSpace();
        }
        floors[i].insertSpace(index, space);
    }

    @Override
    public void deleteSpace(int index) {
        checkFlatIndex(index);
        int i;
        for (i = 0; floors[i].getCountSpace() <= index; i++) {
            index -= floors[i].getCountSpace();
        }
        floors[i].deleteSpace(index);
    }

    @Override
    public Space getBestSpace() {
        return Arrays.stream(floors)
                .map(Floor::getBestSpace)
                .max(Comparator.comparingDouble(Space::getSquare))
                .orElse(null);
    }

    @Override
    public Space[] getSortedSpaceDesc() {
        return Arrays.stream(floors)
                .map(Floor::getSpaces)
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparingDouble(Space::getSquare))
                .toArray(Space[]::new);
    }

    private void checkFloorIndex(int index) {
        if (index < 0 || index >= getCountFloors()) {
            throw new FloorIndexOutOfBoundsException();
        }
    }

    private void checkFlatIndex(int index) {
        if (index < 0 || index >= getCountSpaces()) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<Floor> iterator() {
        return new BuildingIterator(this);
    }
}
