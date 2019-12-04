package my.mapkn3.building;

import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

import java.util.Iterator;

public class SynchronizedFloor implements Floor {
    private Floor floor;

    public SynchronizedFloor(Floor floor) {
        this.floor = floor;
    }

    synchronized public int getSpaceCount() {
        return floor.getSpaceCount();
    }

    synchronized public double getTotalArea() {
        return floor.getTotalArea();
    }

    synchronized public int getTotalRoomsCount() {
        return floor.getTotalRoomsCount();
    }

    synchronized public Space[] getSpaceArray() {
        return floor.getSpaceArray();
    }

    synchronized public Space getSpace(int index) {
        return floor.getSpace(index);
    }

    synchronized public void setSpace(int index, Space space) {
        floor.setSpace(index, space);
    }

    synchronized public void insertSpace(int index, Space space) {
        floor.insertSpace(index, space);
    }

    synchronized public void deleteSpace(int index) {
        floor.deleteSpace(index);
    }

    synchronized public Space getBestSpace() {
        return floor.getBestSpace();
    }

    synchronized public Iterator<Space> iterator() {
        return floor.iterator();
    }

    synchronized public String toString() {
        return floor.toString();
    }

    synchronized public boolean equals(Object o) {
        return floor.equals(o);
    }

    synchronized public int hashCode() {
        return floor.hashCode();
    }
}
