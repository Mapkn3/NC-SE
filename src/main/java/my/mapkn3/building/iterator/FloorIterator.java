package my.mapkn3.building.iterator;

import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FloorIterator implements Iterator<Space> {
    private int cursor;
    private Floor floor;

    public FloorIterator(Floor floor) {
        this.cursor = 0;
        this.floor = floor;
    }

    @Override
    public boolean hasNext() {
        return cursor < floor.getCountSpace();
    }

    @Override
    public Space next() {
        if (cursor >= floor.getCountSpace()) {
            throw new NoSuchElementException();
        }
        return floor.getSpace(cursor++);
    }
}
