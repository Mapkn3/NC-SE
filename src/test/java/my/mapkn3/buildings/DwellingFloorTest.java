package my.mapkn3.buildings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class DwellingFloorTest extends Assert {
    private Flat[] flats;
    private DwellingFloor floor;

    @Before
    public void setUp() {
        flats = new Flat[]{
                new Flat(10, 1),
                new Flat(25, 2),
                new Flat(35, 3)
        };
        floor = new DwellingFloor(flats);
    }

    @Test
    public void getCountFlats() {
        assertEquals(flats.length, floor.getCountFlats());
    }

    @Test
    public void getTotalSquare() {
        assertEquals(Arrays.stream(flats).mapToDouble(Flat::getSquare).sum(), floor.getTotalSquare(), 0);
    }

    @Test
    public void getTotalCountRooms() {
        assertEquals(Arrays.stream(flats).mapToInt(Flat::getCountRooms).sum(), floor.getTotalCountRooms());
    }

    @Test
    public void getFlats() {
        assertArrayEquals(flats, floor.getFlats());
    }

    @Test
    public void getFlat() {
        int index = flats.length / 2;
        assertEquals(flats[index], floor.getFlat(index));
    }

    @Test
    public void getExtraFlat() {
        int index = flats.length;
        assertNull(floor.getFlat(index));
    }

    @Test
    public void setFlat() {
        Flat flat = new Flat(100, 1);
        int index = 0;
        floor.setFlat(index, flat);
        assertEquals(flat, floor.getFlat(index));
    }

    @Test
    public void insertFlat() {
        Flat flat = new Flat(100, 1);
        int index = 0;
        floor.insertFlat(index, flat);
        assertEquals(flat, floor.getFlat(index));
    }

    @Test
    public void deleteFlat() {
        int index = 0;
        floor.deleteFlat(index);
        assertEquals(flats.length - 1, floor.getCountFlats());
    }

    @Test
    public void getBestSpace() {
        Flat bestFlat = Arrays.stream(flats).max(Comparator.comparingDouble(Flat::getSquare)).orElse(null);
        assertEquals(bestFlat, floor.getBestSpace());
    }

    @Test
    public void getNullInsteadBestSpace() {
        DwellingFloor emptyFloor = new DwellingFloor(0);
        assertNull(emptyFloor.getBestSpace());
    }
}