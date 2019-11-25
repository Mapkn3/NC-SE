package my.mapkn3.util;

import my.mapkn3.exceptions.FloorIndexOutOfBoundsException;
import my.mapkn3.exceptions.InexchangeableFloorsException;
import my.mapkn3.exceptions.InexchangeableSpacesException;
import my.mapkn3.exceptions.SpaceIndexOutOfBoundsException;
import my.mapkn3.interfaces.Building;
import my.mapkn3.interfaces.Floor;
import my.mapkn3.interfaces.Space;

public class PlacementExchanger {
    public static boolean isExchangeableSpaces(Space space1, Space space2) {
        return space1.getSquare() == space2.getSquare()
                && space1.getCountRooms() == space2.getCountRooms();
    }

    public static boolean isExchangeableFloors(Floor floor1, Floor floor2) {
        return floor1.getTotalSquare() == floor2.getTotalSquare()
                && floor1.getTotalCountRooms() == floor2.getTotalCountRooms();
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException {
        if (index1 < 0 || index1 >= floor1.getCountSpace()
                || index2 < 0 || index2 >= floor2.getCountSpace()) {
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
        if (index1 < 0 || index1 >= building1.getCountFloors()
                || index2 < 0 || index2 >= building2.getCountFloors()) {
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
