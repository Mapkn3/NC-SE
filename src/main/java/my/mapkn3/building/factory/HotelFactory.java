package my.mapkn3.building.factory;

import my.mapkn3.building.dwelling.Flat;
import my.mapkn3.building.dwelling.hotel.Hotel;
import my.mapkn3.building.dwelling.hotel.HotelFloor;
import my.mapkn3.building.factory.interfaces.BuildingFactory;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

public class HotelFactory implements BuildingFactory {
    @Override
    public Flat createSpace(double area) {
        return new Flat(area);
    }

    @Override
    public Flat createSpace(double area, int roomsCount) {
        return new Flat(area, roomsCount);
    }

    @Override
    public HotelFloor createFloor(int spacesCount) {
        return new HotelFloor(spacesCount);
    }

    @Override
    public HotelFloor createFloor(Space[] spaces) {
        return new HotelFloor(spaces);
    }

    @Override
    public Hotel createBuilding(int floorsCount, int[] spacesCounts) {
        return new Hotel(floorsCount, spacesCounts);
    }

    @Override
    public Hotel createBuilding(Floor[] floors) {
        return new Hotel(floors);
    }
}
