package my.mapkn3.util;

import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;
import my.mapkn3.exception.FloorIndexOutOfBoundsException;
import my.mapkn3.exception.InexchangeableFloorsException;
import my.mapkn3.exception.InexchangeableSpacesException;
import my.mapkn3.exception.SpaceIndexOutOfBoundsException;

public class PlacementExchanger {
    public static boolean isExchangeableSpaces(Space space1, Space space2) {
        return space1.getArea() == space2.getArea()
                && space1.getRoomsCount() == space2.getRoomsCount();
    }

    public static boolean isExchangeableFloors(Floor floor1, Floor floor2) {
        return floor1.getTotalArea() == floor2.getTotalArea()
                && floor1.getTotalRoomsCount() == floor2.getTotalRoomsCount();
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException {
        if (index1 < 0 || index1 >= floor1.getSpaceCount()
                || index2 < 0 || index2 >= floor2.getSpaceCount()) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Space space1 = floor1.getSpace(index1);
        Space space2 = floor2.getSpace(index2);
        if (!isExchangeableSpaces(space1, space2)) {
            throw new InexchangeableSpacesException();
        }
        floor1.setSpace(index1, space2);
        floor2.setSpace(index2, space1);
    }

    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException {
        if (index1 < 0 || index1 >= building1.getFloorsCount()
                || index2 < 0 || index2 >= building2.getFloorsCount()) {
            throw new FloorIndexOutOfBoundsException();
        }
        Floor floor1 = building1.getFloor(index1);
        Floor floor2 = building2.getFloor(index2);
        if (!isExchangeableFloors(floor1, floor2)) {
            throw new InexchangeableFloorsException();
        }
        building1.setFloor(index1, floor2);
        building2.setFloor(index2, floor1);
    }
}
