package my.mapkn3.buildings.dwelling.hotel;

import my.mapkn3.buildings.dwelling.DwellingFloor;
import my.mapkn3.buildings.interfaces.Space;

public class HotelFloor extends DwellingFloor {
    public static final int DEFAULT_STARS = 1;

    private int stars;


    public HotelFloor(int countSpaces) {
        super(countSpaces);
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
}
