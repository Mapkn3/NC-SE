package my.mapkn3.building.dwelling;

import my.mapkn3.exception.InvalidRoomsCountException;
import my.mapkn3.exception.InvalidSpaceAreaException;
import my.mapkn3.building.interfaces.Space;

public class Flat implements Space {
    public static final double DEFAULT_SQUARE = 50.0;
    public static final int DEFAULT_COUNT_ROOMS = 2;
    private double area;
    private int roomsCount;

    public Flat() {
        this(DEFAULT_SQUARE, DEFAULT_COUNT_ROOMS);
    }

    public Flat(double area) {
        if (area <= 0) {
            throw new InvalidSpaceAreaException();
        }
    }

    public Flat(double area, int roomsCount) {
        if (area <= 0) {
            throw new InvalidSpaceAreaException();
        }
        if (roomsCount <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.area = area;
        this.roomsCount = roomsCount;
    }

    @Override
    public double getArea() {
        return area;
    }

    @Override
    public void setArea(double area) {
        if (area <= 0) {
            throw new InvalidSpaceAreaException();
        }
        this.area = area;
    }

    @Override
    public int getRoomsCount() {
        return roomsCount;
    }

    @Override
    public void setRoomsCount(int roomsCount) {
        if (roomsCount <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.roomsCount = roomsCount;
    }
}
