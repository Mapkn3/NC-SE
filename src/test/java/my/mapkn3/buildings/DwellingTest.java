package my.mapkn3.buildings;

import my.mapkn3.buildings.dwelling.Dwelling;
import my.mapkn3.buildings.dwelling.DwellingFloor;
import my.mapkn3.buildings.dwelling.Flat;
import my.mapkn3.exceptions.FloorIndexOutOfBoundsException;
import my.mapkn3.exceptions.SpaceIndexOutOfBoundsException;
import my.mapkn3.buildings.interfaces.Building;
import my.mapkn3.buildings.interfaces.Floor;
import my.mapkn3.buildings.interfaces.Space;
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
                .mapToInt(Floor::getCountSpace)
                .sum();
        assertEquals(countFlats, dwelling.getCountSpaces());
    }

    @Test
    public void getTotalSquare() {
        double totalSquare = Arrays.stream(floors)
                .mapToDouble(Floor::getTotalSquare)
                .sum();
        assertEquals(totalSquare, dwelling.getTotalSquare(), 0);
    }

    @Test
    public void getTotalCountRooms() {
        int totalCountRooms = Arrays.stream(floors)
                .mapToInt(Floor::getTotalCountRooms)
                .sum();
        assertEquals(totalCountRooms, dwelling.getTotalCountRooms());
    }

    @Test
    public void getFloors() {
        assertArrayEquals(floors, dwelling.getFloorArray());
    }

    @Test
    public void getFloor() {
        int index = floors.length / 2;
        assertEquals(floors[index], dwelling.getFloor(index));
    }

    @Test(expected = FloorIndexOutOfBoundsException.class)
    public void getExtraFloor() {
        int index = -1;
        dwelling.getFloor(index);
    }

    @Test
    public void setFloor() {
        int index = 0;
        Floor floor = new DwellingFloor(new Flat[]{new Flat()});
        dwelling.setFloor(index, floor);
        assertEquals(floor, dwelling.getFloor(index));
    }

    @Test
    public void getFlat() {
        assertEquals(floors[1].getSpace(1), dwelling.getSpace(4));
    }

    @Test(expected = SpaceIndexOutOfBoundsException.class)
    public void getExtraFlat() {
        int index = -1;
        dwelling.getSpace(index);
    }

    @Test
    public void setFlat() {
        Space space = new Flat();
        int index = 3;
        dwelling.setSpace(index, space);
        assertEquals(space, dwelling.getSpace(index));
    }

    @Test
    public void deleteFlat() {
        int count = Arrays.stream(floors)
                .mapToInt(DwellingFloor::getCountSpace)
                .sum() - 1;
        dwelling.deleteSpace(0);
        assertEquals(count, dwelling.getCountSpaces());
    }

    @Test
    public void getBestSpace() {
        Space flat = Arrays.stream(floors)
                .map(DwellingFloor::getBestSpace)
                .max(Comparator.comparingDouble(Space::getSquare))
                .orElse(null);
        assertEquals(flat, dwelling.getBestSpace());
    }

    @Test
    public void getNullInsteadBestSpace() {
        Building emptyDwelling = new Dwelling(0, new int[]{0});
        assertNull(emptyDwelling.getBestSpace());
    }

    @Test
    public void getSortedFlatDesc() {
        Space[] sortedSpaces = Arrays.stream(floors)
                .map(Floor::getSpaces)
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparingDouble(Space::getSquare))
                .toArray(Space[]::new);
        assertArrayEquals(sortedSpaces, dwelling.getSortedSpaceDesc());
    }
}