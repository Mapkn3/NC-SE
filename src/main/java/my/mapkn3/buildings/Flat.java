package my.mapkn3.buildings;

import my.mapkn3.exceptions.InvalidRoomsCountException;
import my.mapkn3.exceptions.InvalidSpaceAreaException;

public class Flat {
    public static final double DEFAULT_SQUARE = 50.0;
    public static final int DEFAULT_COUNT_ROOMS = 2;
    private double square;
    private int countRooms;

    public Flat() {
        this(DEFAULT_SQUARE, DEFAULT_COUNT_ROOMS);
    }

    public Flat(double square) {
        if (square <= 0) {
            throw new InvalidSpaceAreaException();
        }
    }

    public Flat(double square, int countRooms) {
        if (square <= 0) {
            throw new InvalidSpaceAreaException();
        }
        if (countRooms <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.square = square;
        this.countRooms = countRooms;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        if (square <= 0) {
            throw new InvalidSpaceAreaException();
        }
        this.square = square;
    }

    public int getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(int countRooms) {
        if (countRooms <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.countRooms = countRooms;
    }
}
