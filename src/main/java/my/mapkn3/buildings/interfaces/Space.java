package my.mapkn3.buildings.interfaces;

import java.io.Serializable;

public interface Space extends Serializable {
    double getSquare();

    void setSquare(double square);

    int getCountRooms();

    void setCountRooms(int countRooms);
}
