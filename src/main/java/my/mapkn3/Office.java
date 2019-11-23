package my.mapkn3;

public class Office {
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
