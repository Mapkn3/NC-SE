package my.mapkn3.building.interfaces;

import java.io.Serializable;

public interface Space extends Serializable, Cloneable {
    double getArea();

    void setArea(double area);

    int getRoomsCount();

    void setRoomsCount(int roomsCount);
}
