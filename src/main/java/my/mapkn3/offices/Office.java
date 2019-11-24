package my.mapkn3.offices;

import my.mapkn3.exceptions.InvalidRoomsCountException;
import my.mapkn3.exceptions.InvalidSpaceAreaException;

public class Office implements my.mapkn3.interfaces.Space {
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
