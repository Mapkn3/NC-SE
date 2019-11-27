package my.mapkn3.buildings.office;

import my.mapkn3.exceptions.InvalidRoomsCountException;
import my.mapkn3.exceptions.InvalidSpaceAreaException;
import my.mapkn3.buildings.interfaces.Space;

public class Office implements Space {
    public static final double DEFAULT_SQUARE = 250.0;
    public static final int DEFAULT_COUNT_ROOMS = 1;
    private double square;
    private int countRooms;

    public Office() {
        this(DEFAULT_SQUARE, DEFAULT_COUNT_ROOMS);
    }

    public Office(double square) {
        this(square, DEFAULT_COUNT_ROOMS);
    }

    public Office(double square, int countRooms) {
        if (square <= 0) {
            throw new InvalidSpaceAreaException();
        }
        if (countRooms <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.square = square;
        this.countRooms = countRooms;
    }

    @Override
    public double getSquare() {
        return square;
    }

    @Override
    public void setSquare(double square) {
        if (square <= 0) {
            throw new InvalidSpaceAreaException();
        }
        this.square = square;
    }

    @Override
    public int getCountRooms() {
        return countRooms;
    }

    @Override
    public void setCountRooms(int countRooms) {
        if (countRooms <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.countRooms = countRooms;
    }
}
