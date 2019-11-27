package my.mapkn3.util;

import my.mapkn3.building.dwelling.Dwelling;
import my.mapkn3.building.dwelling.DwellingFloor;
import my.mapkn3.building.dwelling.Flat;
import my.mapkn3.building.factory.DwellingFactory;
import my.mapkn3.building.factory.interfaces.BuildingFactory;
import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Buildings {
    private static BuildingFactory buildingFactory = new DwellingFactory();

    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static Space createSpace(double area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(int roomsCount, double area) {
        return buildingFactory.createSpace(roomsCount, area);
    }

    public static Floor createFloor(int spacesCount) {
        return buildingFactory.createFloor(spacesCount);
    }

    public static Floor createFloor(Space[] spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Building createBuilding(int floorsCount, int[] spacesCounts) {
        return buildingFactory.createBuilding(floorsCount, spacesCounts);
    }

    public static Building createBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }

    public static void outputBuilding(Building building, OutputStream out) throws IOException {
        String data = getStringFromBuilding(building);
        out.write(data.getBytes());
    }

    public static Building inputBuilding(InputStream in) throws IOException {
        int bufferSize = 100;
        byte[] buffer = new byte[bufferSize];
        int readBytes;
        List<Byte> bytes = new ArrayList<>();
        while ((readBytes = in.read(buffer)) != -1) {
            for (int i = 0; i < readBytes; i++) {
                bytes.add(buffer[i]);
            }
        }
        byte[] rawData = new byte[bytes.size()];
        for (int i = 0; i < rawData.length; i++) {
            rawData[i] = bytes.get(i);
        }
        return getBuildingFromString(new String(rawData));
    }

    public static void writeBuilding(Building building, Writer out) throws IOException {
        String data = getStringFromBuilding(building);
        out.write(data);
    }

    public static Building readBuilding(Reader in) throws IOException {
        int bufferSize = 100;
        char[] buffer = new char[bufferSize];
        int readChars;
        List<Character> chars = new ArrayList<>();
        while ((readChars = in.read(buffer)) != -1) {
            for (int i = 0; i < readChars; i++) {
                chars.add(buffer[i]);
            }
        }
        char[] rawData = new char[chars.size()];
        for (int i = 0; i < rawData.length; i++) {
            rawData[i] = chars.get(i);
        }
        return getBuildingFromString(String.valueOf(rawData));
    }

    private static String getStringFromBuilding(Building building) {
        List<String> data = new ArrayList<>();
        data.add(String.valueOf(building.getFloorsCount()));
        for (Floor floor : building.getFloorArray()) {
            data.add(String.valueOf(floor.getCountSpace()));
            for (Space space : floor.getSpaces()) {
                data.add(String.format("%.1f %d", space.getArea(), space.getRoomsCount())
                        .replace(',', '.'));
            }
        }
        return String.join(" ", data);
    }

    private static Building getBuildingFromString(String data) {
        String[] rawData = data.split(" ");
        int i = 0;
        Floor[] floors = new Floor[Integer.parseInt(rawData[i++])];
        for (int j = 0; j < floors.length; j++) {
            Space[] spaces = new Space[Integer.parseInt(rawData[i++])];
            for (int k = 0; k < spaces.length; k++) {
                spaces[k] = createSpace(Integer.parseInt(rawData[i++]), Double.parseDouble(rawData[i++]));
            }
            floors[j] = createFloor(spaces);
        }
        return createBuilding(floors);
    }
}
