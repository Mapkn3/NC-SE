package my.mapkn3.building.iterator;

import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BuildingIterator implements Iterator<Floor> {
    private int cursor;
    private Building building;

    public BuildingIterator(Building building) {
        this.cursor = 0;
        this.building = building;
    }

    @Override
    public boolean hasNext() {
        return cursor < building.getFloorsCount();
    }

    @Override
    public Floor next() {
        if (cursor >= building.getFloorsCount()) {
            throw new NoSuchElementException();
        }
        return this.building.getFloor(cursor++);
    }
}
