package my.mapkn3.building.office;

import my.mapkn3.building.interfaces.Space;
import my.mapkn3.exception.InvalidRoomsCountException;
import my.mapkn3.exception.InvalidSpaceAreaException;

public class Office implements Space {
    public static final double DEFAULT_SQUARE = 250.0;
    public static final int DEFAULT_COUNT_ROOMS = 1;
    private double area;
    private int roomsCount;

    public Office() {
        this(DEFAULT_SQUARE, DEFAULT_COUNT_ROOMS);
    }

    public Office(double area) {
        this(area, DEFAULT_COUNT_ROOMS);
    }

    public Office(double area, int roomsCount) {
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
        return String.format("Office (%d, %.1f)", roomsCount, area);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Office)) return false;

        Office office = (Office) o;

        if (Double.compare(office.area, area) != 0) return false;
        return roomsCount == office.roomsCount;
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
