package my.mapkn3.building.factory.interfaces;

import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

public interface BuildingFactory {
    Space createSpace(double area);

    Space createSpace(int roomsCount, double area);

    Floor createFloor(int spacesCount);

    Floor createFloor(Space[] spaces);

    Building createBuilding(int floorsCount, int[] spacesCounts);

    Building createBuilding(Floor[] floors);
}
