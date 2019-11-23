package my.mapkn3.buildings;

import java.util.Arrays;
import java.util.Comparator;

public class DwellingFloor {
    private Flat[] flats;

    public DwellingFloor(int countFlats) {
        this.flats = new Flat[countFlats];
    }

    public DwellingFloor(Flat[] flats) {
        this.flats = flats;
    }

    public int getCountFlats() {
        return flats.length;
    }

    public double getTotalSquare() {
        return Arrays.stream(flats).mapToDouble(Flat::getSquare).sum();
    }

    public int getTotalCountRooms() {
        return Arrays.stream(flats).mapToInt(Flat::getCountRooms).sum();
    }

    public Flat[] getFlats() {
        return flats;
    }

    public Flat getFlat(int index) {
        Flat flat = null;
        if (index >= 0 && index < flats.length) {
            flat = flats[index];
        }
        return flat;
    }

    public void setFlat(int index, Flat flat) {
        if (index >= 0 && index < flats.length) {
            flats[index] = flat;
        }
    }

    public void insertFlat(int index, Flat flat) {
        if (index >= 0 && index < flats.length) {
            Flat[] extendedFlats = new Flat[flats.length + 1];
            int i = 0;
            int j = 0;
            extendedFlats[j++] = (i == index) ? flat : flats[i++];
            flats = extendedFlats;
        }
    }

    public void deleteFlat(int index) {
        if (index >= 0 && index < flats.length) {
            Flat[] compressedFlats = new Flat[flats.length - 1];
            int j = 0;
            for (int i = 0; i < flats.length; i++) {
                if (i == index) {
                    continue;
                }
                compressedFlats[j++] = flats[i];
            }
            flats = compressedFlats;
        }
    }

    public Flat getBestSpace() {
        return Arrays.stream(flats).max(Comparator.comparingDouble(Flat::getSquare)).orElse(null);
    }
}
