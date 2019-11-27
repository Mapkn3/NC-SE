package my.mapkn3.building;

import my.mapkn3.building.dwelling.Flat;
import my.mapkn3.building.interfaces.Space;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FlatTest extends Assert {

    private Space flat;

    @Before
    public void setUp() {
        flat = new Flat();
    }

    @Test
    public void setSquare() {
        double square = 18.0;
        flat.setArea(square);
        assertEquals(square, flat.getArea(), 0);
    }

    @Test
    public void setCountRooms() {
        int countRooms = 1;
        flat.setRoomsCount(countRooms);
        assertEquals(countRooms, flat.getRoomsCount());
    }
}