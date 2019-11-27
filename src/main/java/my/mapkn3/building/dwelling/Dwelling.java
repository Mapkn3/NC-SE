package my.mapkn3.building.dwelling;

import my.mapkn3.building.iterator.BuildingIterator;
import my.mapkn3.exception.FloorIndexOutOfBoundsException;
import my.mapkn3.exception.SpaceIndexOutOfBoundsException;
import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class Dwelling implements Building {
    private Floor[] floors;

    public Dwelling(int floorsCount, int[] spacesCounts) {
        this.floors = new Floor[floorsCount];
        for (int i = 0; i < floorsCount; i++) {
            this.floors[i] = new DwellingFloor(spacesCounts[i]);
        }
    }

    public Dwelling(Floor[] floors) {
        this.floors = floors;
    }

    public int getFloorsCount() {
        return floors.length;
    }

    @Override
    public int getSpacesCount() {
        return Arrays.stream(floors)
                .mapToInt(Floor::getCountSpace)
                .sum();
    }

    @Override
    public double getTotalArea() {
        return Arrays.stream(floors)
                .mapToDouble(Floor::getTotalArea)
                .sum();
    }

    @Override
    public int getTotalRoomsCount() {
        return Arrays.stream(floors)
                .mapToInt(Floor::getTotalRoomsCount)
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
                .max(Comparator.comparingDouble(Space::getArea))
                .orElse(null);
    }

    @Override
    public Space[] getSortedSpaceDesc() {
        return Arrays.stream(floors)
                .map(Floor::getSpaces)
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparingDouble(Space::getArea))
                .toArray(Space[]::new);
    }

    private void checkFloorIndex(int index) {
        if (index < 0 || index >= getFloorsCount()) {
            throw new FloorIndexOutOfBoundsException();
        }
    }

    private void checkFlatIndex(int index) {
        if (index < 0 || index >= getSpacesCount()) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public Iterator<Floor> iterator() {
        return new BuildingIterator(this);
    }
}
