package my.mapkn3.building.dwelling;

import my.mapkn3.building.interfaces.Space;
import my.mapkn3.exception.InvalidRoomsCountException;
import my.mapkn3.exception.InvalidSpaceAreaException;

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

    @Override
    public String toString() {
        return String.format("Flat (%d, %.1f)", roomsCount, area);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flat flat = (Flat) o;

        if (Double.compare(flat.area, area) != 0) return false;
        return roomsCount == flat.roomsCount;
    }

    @Override
    public int hashCode() {
        long areaBits = Double.doubleToLongBits(area);
        int firstBits = (int) ((areaBits & 0xffffffff00000000L) >>> 32);
        int lastBits = (int) (areaBits & 0xffffffffL);
        return roomsCount ^ firstBits ^ lastBits;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
