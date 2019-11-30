package my.mapkn3.building.dwelling.hotel;

import my.mapkn3.building.dwelling.DwellingFloor;
import my.mapkn3.building.interfaces.Space;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HotelFloor extends DwellingFloor {
    public static final int DEFAULT_STARS = 1;

    private int stars;


    public HotelFloor(int spacesCount) {
        super(spacesCount);
        this.stars = DEFAULT_STARS;
    }

    public HotelFloor(Space[] spaces) {
        super(spaces);
        this.stars = DEFAULT_STARS;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        return String.format("HotelFloor (%d, %d, %s)",
                stars,
                getSpaceCount(),
                Arrays.stream(getSpaceArray())
                        .map(Object::toString)
                        .collect(Collectors.joining(", ")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelFloor)) return false;

        HotelFloor hotelFloor = (HotelFloor) o;

        if (getStars() != hotelFloor.getStars()) return false;
        if (getSpaceCount() != hotelFloor.getSpaceCount()) return false;
        for (int i = 0; i < getSpaceCount(); i++) {
            if (!getSpace(i).equals(hotelFloor.getSpace(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.stream(getSpaceArray())
                .mapToInt(Object::hashCode)
                .reduce(getSpaceCount() ^ getStars(), (accum, next) -> accum ^ next);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
