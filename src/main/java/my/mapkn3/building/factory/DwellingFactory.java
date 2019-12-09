package my.mapkn3.building.factory;

import my.mapkn3.building.dwelling.Dwelling;
import my.mapkn3.building.dwelling.DwellingFloor;
import my.mapkn3.building.dwelling.Flat;
import my.mapkn3.building.factory.interfaces.BuildingFactory;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

public class DwellingFactory implements BuildingFactory {

    @Override
    public Flat createSpace(double area) {
        return new Flat(area);
    }

    @Override
    public Flat createSpace(double area, int roomsCount) {
        return new Flat(area, roomsCount);
    }

    @Override
    public DwellingFloor createFloor(int spacesCount) {
        return new DwellingFloor(spacesCount);
    }

    @Override
    public DwellingFloor createFloor(Space[] spaces) {
        return new DwellingFloor(spaces);
    }

    @Override
    public Dwelling createBuilding(int floorsCount, int[] spacesCounts) {
        return new Dwelling(floorsCount, spacesCounts);
    }

    @Override
    public Dwelling createBuilding(Floor[] floors) {
        return new Dwelling(floors);
    }
}
