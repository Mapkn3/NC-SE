package my.mapkn3.buildings.interfaces;

import java.io.Serializable;

public interface Floor extends Serializable, Iterable<Space> {
    int getCountSpace();

    double getTotalSquare();

    int getTotalCountRooms();

    Space[] getSpaces();

    Space getSpace(int index);

    void setSpace(int index, Space space);

    void insertSpace(int index, Space space);

    void deleteSpace(int index);

    Space getBestSpace();
}
