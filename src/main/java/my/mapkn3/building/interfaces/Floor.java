package my.mapkn3.building.interfaces;

import java.io.Serializable;

public interface Floor extends Serializable, Iterable<Space>, Cloneable {
    int getSpaceCount();

    double getTotalArea();

    int getTotalRoomsCount();

    Space[] getSpaceArray();

    Space getSpace(int index);

    void setSpace(int index, Space space);

    void insertSpace(int index, Space space);

    void deleteSpace(int index);

    Space getBestSpace();
}
