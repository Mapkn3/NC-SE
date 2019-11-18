package my.mapkn3.buildings;

public class Flat {
    public static final double DEFAULT_SQUARE = 50.0;
    public static final int DEFAULT_COUNT_ROOMS = 2;
    private double square;
    private int countRooms;

    public Flat() {
        this(DEFAULT_SQUARE, DEFAULT_COUNT_ROOMS);
    }

    public Flat(double square) {
        this(square, DEFAULT_COUNT_ROOMS);
    }

    public Flat(double square, int countRooms) {
        this.square = square;
        this.countRooms = countRooms;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public int getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(int countRooms) {
        this.countRooms = countRooms;
    }
}
