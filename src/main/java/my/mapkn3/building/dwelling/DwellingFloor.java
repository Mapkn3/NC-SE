package my.mapkn3.building.dwelling;

import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;
import my.mapkn3.building.iterator.FloorIterator;
import my.mapkn3.exception.SpaceIndexOutOfBoundsException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DwellingFloor implements Floor {
    private Space[] spaces;

    public DwellingFloor(int countSpaces) {
        this(Stream.generate(Flat::new)
                .limit(countSpaces)
                .toArray(Flat[]::new));
    }

    public DwellingFloor(Space[] spaces) {
        this.spaces = spaces;
    }

    @Override
    public int getSpaceCount() {
        return spaces.length;
    }

    @Override
    public double getTotalArea() {
        return Arrays.stream(spaces)
                .mapToDouble(Space::getArea)
                .sum();
    }

    @Override
    public int getTotalRoomsCount() {
        return Arrays.stream(spaces)
                .mapToInt(Space::getRoomsCount)
                .sum();
    }

    @Override
    public Space[] getSpaceArray() {
        return spaces;
    }

    @Override
    public Space getSpace(int index) {
        checkIndex(index);
        return spaces[index];
    }

    @Override
    public void setSpace(int index, Space space) {
        checkIndex(index);
        spaces[index] = space;
    }

    @Override
    public void insertSpace(int index, Space space) {
        checkIndex(index);
        Space[] extendedSpaces = new Space[spaces.length + 1];
        for (int i = 0, j = 0; i < extendedSpaces.length; i++) {
            extendedSpaces[i] = (j == index) ? space : spaces[j++];
        }
        spaces = extendedSpaces;
    }

    @Override
    public void deleteSpace(int index) {
        checkIndex(index);
        Space[] compressedSpaces = new Space[spaces.length - 1];
        for (int i = 0, j = 0; i < spaces.length; i++) {
            if (i == index) {
                continue;
            }
            compressedSpaces[j++] = spaces[i];
        }
        spaces = compressedSpaces;
    }

    @Override
    public Space getBestSpace() {
        return Arrays.stream(spaces)
                .max(Comparator.comparingDouble(Space::getArea))
                .orElse(null);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= spaces.length) {
            throw new SpaceIndexOutOfBoundsException();
        }
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
