package my.mapkn3.building.dwelling.hotel;

import my.mapkn3.building.dwelling.Dwelling;
import my.mapkn3.building.dwelling.DwellingFloor;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

import java.util.Arrays;
import java.util.Comparator;

public class Hotel extends Dwelling {
    public Hotel(int floorsCount, int[] spacesCount) {
        super(floorsCount, spacesCount);
    }

    public Hotel(Floor[] floors) {
        super(floors);
    }

    public int getStarsOfHotel() {
        return Arrays.stream(getFloorArray())
                .filter(floor -> floor instanceof HotelFloor)
                .map(floor -> (HotelFloor) floor)
                .mapToInt(HotelFloor::getStars)
                .max()
                .orElse(0);
    }

    @Override
    public Space getBestSpace() {
        return Arrays.stream(getFloorArray())
                .filter(floor -> (floor instanceof HotelFloor) && (floor.getBestSpace() != null))
                .map(floor -> (HotelFloor) floor)
                .max(Comparator.comparingDouble(
                        hotelFloor -> hotelFloor.getBestSpace().getArea() * getCoeff(hotelFloor.getStars())))
                .map(DwellingFloor::getBestSpace)
                .orElse(null);
    }

    private double getCoeff(int stars) {
        double coeff;
        switch (stars) {
            case 1:
                coeff = 0.25;
                break;
            case 2:
                coeff = 0.5;
                break;
            case 3:
                coeff = 1;
                break;
            case 4:
                coeff = 1.25;
                break;
            case 5:
                coeff = 1.5;
                break;
            default:
                coeff = 0;
        }
        return coeff;
    }
}
