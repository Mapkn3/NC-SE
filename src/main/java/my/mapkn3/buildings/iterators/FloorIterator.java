package my.mapkn3.buildings.iterators;

import my.mapkn3.buildings.interfaces.Floor;
import my.mapkn3.buildings.interfaces.Space;

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
