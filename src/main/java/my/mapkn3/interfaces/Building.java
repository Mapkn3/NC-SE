package my.mapkn3.interfaces;

public interface Building {
    int getCountFloors();

    int getCountSpaces();

    double getTotalSquare();

    int getTotalCountRooms();

    Floor[] getFloorArray();

    Floor getFloor(int index);

    void setFloor(int index, Floor floor);

    Space getSpace(int index);

    void setSpace(int index, Space space);

    void insertSpace(int index, Space space);

    void deleteSpace(int index);

    Space getBestSpace();

    Space[] getSortedSpaceDesc();
}