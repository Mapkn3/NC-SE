package my.mapkn3.buildings.iterators;

import my.mapkn3.buildings.interfaces.Building;
import my.mapkn3.buildings.interfaces.Floor;

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
        return cursor < building.getCountFloors();
    }

    @Override
    public Floor next() {
        if (cursor >= building.getCountFloors()) {
            throw new NoSuchElementException();
        }
        return this.building.getFloor(cursor++);
    }
}
