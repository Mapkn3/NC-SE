package my.mapkn3.buildings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class DwellingTest extends Assert {
    private DwellingFloor[] floors;
    private Dwelling dwelling;

    @Before
    public void setUp() {
        Flat[] flatsForFirstFloor = new Flat[]{
                new Flat(10, 1),
                new Flat(25, 2),
                new Flat(35, 3)
        };
        Flat[] flatsForSecondFloor = new Flat[]{
                new Flat(18, 1),
                new Flat(48, 2)
        };
        floors = new DwellingFloor[]{
                new DwellingFloor(flatsForFirstFloor),
                new DwellingFloor(flatsForSecondFloor)
        };
        dwelling = new Dwelling(floors);
    }

    @Test
    public void getCountFloors() {
        assertEquals(floors.length, dwelling.getCountFloors());
    }

    @Test
    public void getCountFlats() {
        int countFlats = Arrays.stream(floors)
                .mapToInt(DwellingFloor::getCountFlats)
                .sum();
        assertEquals(countFlats, dwelling.getCountFlats());
    }

    @Test
    public void getTotalSquare() {
        double totalSquare = Arrays.stream(floors)
                .mapToDouble(DwellingFloor::getTotalSquare)
                .sum();
        assertEquals(totalSquare, dwelling.getTotalSquare(), 0);
    }

    @Test
    public void getTotalCountRooms() {
        assertEquals(Arrays.stream(floors).mapToInt(DwellingFloor::getTotalCountRooms).sum(), dwelling.getTotalCountRooms());
    }

    @Test
    public void getFloors() {
        assertArrayEquals(floors, dwelling.getFloors());
    }

    @Test
    public void getFloor() {
        int index = floors.length / 2;
        assertEquals(floors[index], dwelling.getFloor(index));
    }

    @Test
    public void getExtraFloor() {
        int index = floors.length;
        assertNull(dwelling.getFloor(index));
    }

    @Test
    public void setFloor() {
        int index = 0;
        DwellingFloor floor = new DwellingFloor(new Flat[]{new Flat()});
        dwelling.setFloor(index, floor);
        assertEquals(floor, dwelling.getFloor(index));
    }

    @Test
    public void getFlat() {
        assertEquals(floors[1].getFlat(1), dwelling.getFlat(4));
    }

    @Test
    public void getExtraFlat() {
        int index = Arrays.stream(floors).mapToInt(DwellingFloor::getCountFlats).sum();
        assertNull(dwelling.getFlat(index));
    }

    @Test
    public void setFlat() {
        Flat flat = new Flat();
        int index = 3;
        dwelling.setFlat(index, flat);
        assertEquals(flat, dwelling.getFlat(index));
    }

    @Test
    public void deleteFlat() {
        int count = Arrays.stream(floors)
                .mapToInt(DwellingFloor::getCountFlats)
                .sum() - 1;
        dwelling.deleteFlat(0);
        assertEquals(count, dwelling.getCountFlats());
    }

    @Test
    public void getBestSpace() {
        Flat flat = Arrays.stream(floors)
                .map(DwellingFloor::getBestSpace)
                .max(Comparator.comparingDouble(Flat::getSquare))
                .orElse(null);
        assertEquals(flat, dwelling.getBestSpace());
    }

    @Test
    public void getNullInsteadBestSpace() {
        Dwelling emptyDwelling = new Dwelling(0, new int[] {0});
        assertNull(emptyDwelling.getBestSpace());
    }

    @Test
    public void getSortedFlatDesc() {
        Flat[] sortedFlats = Arrays.stream(floors)
                .map(DwellingFloor::getFlats)
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparingDouble(Flat::getSquare))
                .toArray(Flat[]::new);
        assertArrayEquals(sortedFlats, dwelling.getSortedFlatDesc());
    }
}