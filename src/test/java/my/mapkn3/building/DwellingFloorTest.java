package my.mapkn3.building;

import my.mapkn3.building.dwelling.DwellingFloor;
import my.mapkn3.building.dwelling.Flat;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;
import my.mapkn3.exception.SpaceIndexOutOfBoundsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class DwellingFloorTest extends Assert {
    private Flat[] spaces;
    private Floor floor;

    @Before
    public void setUp() {
        spaces = new Flat[]{
                new Flat(10, 1),
                new Flat(25, 2),
                new Flat(35, 3)
        };
        floor = new DwellingFloor(spaces);
    }

    @Test
    public void getCountFlats() {
        assertEquals(spaces.length, floor.getSpaceCount());
    }

    @Test
    public void getTotalSquare() {
        double totalSquare = Arrays.stream(spaces)
                .mapToDouble(Space::getArea)
                .sum();
        assertEquals(totalSquare, floor.getTotalArea(), 0);
    }

    @Test
    public void getTotalCountRooms() {
        int totalCountRooms = Arrays.stream(spaces)
                .mapToInt(Space::getRoomsCount)
                .sum();
        assertEquals(totalCountRooms, floor.getTotalRoomsCount());
    }

    @Test
    public void getFlats() {
        assertArrayEquals(spaces, floor.getSpaceArray());
    }

    @Test
    public void getFlat() {
        int index = spaces.length / 2;
        assertEquals(spaces[index], floor.getSpace(index));
    }

    @Test(expected = SpaceIndexOutOfBoundsException.class)
    public void getExtraFlat() {
        int index = spaces.length;
        floor.getSpace(index);
    }

    @Test
    public void setFlat() {
        Space space = new Flat(100, 1);
        int index = 0;
        floor.setSpace(index, space);
        assertEquals(space, floor.getSpace(index));
    }

    @Test
    public void insertFlat() {
        Space space = new Flat(100, 1);
        int index = 0;
        floor.insertSpace(index, space);
        assertEquals(space, floor.getSpace(index));
    }

    @Test
    public void deleteFlat() {
        int index = 0;
        floor.deleteSpace(index);
        assertEquals(spaces.length - 1, floor.getSpaceCount());
    }

    @Test
    public void getBestSpace() {
        Space bestSpace = Arrays.stream(spaces)
                .max(Comparator.comparingDouble(Space::getArea))
                .orElse(null);
        assertEquals(bestSpace, floor.getBestSpace());
    }

    @Test
    public void getNullInsteadBestSpace() {
        Floor emptyFloor = new DwellingFloor(0);
        assertNull(emptyFloor.getBestSpace());
    }
}