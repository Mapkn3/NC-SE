package my.mapkn3.buildings.dwelling;

import my.mapkn3.buildings.iterators.FloorIterator;
import my.mapkn3.exceptions.SpaceIndexOutOfBoundsException;
import my.mapkn3.buildings.interfaces.Floor;
import my.mapkn3.buildings.interfaces.Space;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
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
    public int getCountSpace() {
        return spaces.length;
    }

    @Override
    public double getTotalSquare() {
        return Arrays.stream(spaces)
                .mapToDouble(Space::getSquare)
                .sum();
    }

    @Override
    public int getTotalCountRooms() {
        return Arrays.stream(spaces)
                .mapToInt(Space::getCountRooms)
                .sum();
    }

    @Override
    public Space[] getSpaces() {
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
                .max(Comparator.comparingDouble(Space::getSquare))
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
}