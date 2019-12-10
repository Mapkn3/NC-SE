package my.mapkn3.building.dwelling;

import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;
import my.mapkn3.building.iterator.FloorIterator;
import my.mapkn3.exception.SpaceIndexOutOfBoundsException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DwellingFloor implements Floor {
    private List<Space> spaces;

    public DwellingFloor(int countSpaces) {
        this(Stream.generate(Flat::new)
                .limit(countSpaces)
                .toArray(Flat[]::new));
    }

    public DwellingFloor(Space... spaces) {
        this.spaces = Arrays.asList(spaces);
    }

    @Override
    public int getSpaceCount() {
        return spaces.size();
    }

    @Override
    public double getTotalArea() {
        return spaces.stream()
                .mapToDouble(Space::getArea)
                .sum();
    }

    @Override
    public int getTotalRoomsCount() {
        return spaces.stream()
                .mapToInt(Space::getRoomsCount)
                .sum();
    }

    @Override
    public Space[] getSpaceArray() {
        return spaces.toArray(new Space[0]);
    }

    @Override
    public Space getSpace(int index) {
        try {
            return spaces.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public void setSpace(int index, Space space) {
        try {
            spaces.set(index, space);
        } catch (IndexOutOfBoundsException e) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public void insertSpace(int index, Space space) {
        try {
            spaces.add(index, space);
        } catch (IndexOutOfBoundsException e) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public void deleteSpace(int index) {
        try {
            spaces.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    @Override
    public Space getBestSpace() {
        return spaces.stream()
                .max(Comparator.comparingDouble(Space::getArea))
                .orElse(null);
    }

    @Override
    public Iterator<Space> iterator() {
        return new FloorIterator(this);
    }

    @Override
    public String toString() {
        return String.format("DwellingFloor (%d, %s)",
                getSpaceCount(),
                Arrays.stream(getSpaceArray())
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DwellingFloor)) return false;

        DwellingFloor dwellingFloor = (DwellingFloor) o;

        if (getSpaceCount() != dwellingFloor.getSpaceCount()) return false;
        for (int i = 0; i < getSpaceCount(); i++) {
            if (!getSpace(i).equals(dwellingFloor.getSpace(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.stream(getSpaceArray())
                .mapToInt(Object::hashCode)
                .reduce(getSpaceCount(), (accum, next) -> accum ^ next);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
