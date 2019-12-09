package my.mapkn3.building.factory;

import my.mapkn3.building.factory.interfaces.BuildingFactory;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;
import my.mapkn3.building.office.Office;
import my.mapkn3.building.office.OfficeBuilding;
import my.mapkn3.building.office.OfficeFloor;

public class OfficeFactory implements BuildingFactory {
    @Override
    public Office createSpace(double area) {
        return new Office(area);
    }

    @Override
    public Office createSpace(double area, int roomsCount) {
        return new Office(area, roomsCount);
    }

    @Override
    public OfficeFloor createFloor(int spacesCount) {
        return new OfficeFloor(spacesCount);
    }

    @Override
    public OfficeFloor createFloor(Space[] spaces) {
        return new OfficeFloor(spaces);
    }

    @Override
    public OfficeBuilding createBuilding(int floorsCount, int[] spacesCounts) {
        return new OfficeBuilding(floorsCount, spacesCounts);
    }

    @Override
    public OfficeBuilding createBuilding(Floor[] floors) {
        return new OfficeBuilding(floors);
    }
}
