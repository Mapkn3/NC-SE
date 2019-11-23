package my.mapkn3.buildings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FlatTest extends Assert {

    private Flat flat;

    @Before
    public void setUp() {
        flat = new Flat();
    }

    @Test
    public void checkSetSquare() {
        double square = 18.0;
        flat.setSquare(square);
        assertEquals(square, flat.getSquare(), 0);
    }

    @Test
    public void checkSetCountRooms() {
        int countRooms = 1;
        flat.setCountRooms(countRooms);
        assertEquals(countRooms, flat.getCountRooms());
    }
}