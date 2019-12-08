package my.mapkn3.util;

import my.mapkn3.building.SynchronizedFloor;
import my.mapkn3.building.factory.DwellingFactory;
import my.mapkn3.building.factory.interfaces.BuildingFactory;
import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
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

    public Floor synchronizedFloor(Floor floor) {
        return new SynchronizedFloor(floor);
    }

    public static Building createBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }

    public static void outputBuilding(Building building, OutputStream out) throws IOException {
        String data = getStringFromBuilding(building);
        out.write(data.getBytes());
        out.write('\n');
        out.flush();
    }

    public static Building inputBuilding(InputStream in) throws IOException {
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        while (in.read(buffer) != -1) {
            byteArrayInputStream.write(buffer);
        }
        return getBuildingFromString(byteArrayInputStream.toString());
    }

    public static void writeBuilding(Building building, Writer out) throws IOException {
        String data = getStringFromBuilding(building);
        out.write(data);
        out.write('\n');
        out.flush();
    }

    public static Building readBuilding(Reader in) throws IOException {
        int bufferSize = 1024;
        char[] buffer = new char[bufferSize];
        StringWriter stringWriter = new StringWriter();
        while (in.read(buffer) != -1) {
            stringWriter.write(buffer);
        }
        return getBuildingFromString(stringWriter.toString());
    }

    private static String getStringFromBuilding(Building building) {
        List<String> data = new ArrayList<>();
        data.add(String.valueOf(building.getFloorsCount()));
        for (Floor floor : building.getFloorArray()) {
            data.add(String.valueOf(floor.getSpaceCount()));
            for (Space space : floor.getSpaceArray()) {
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
                double area = Double.parseDouble(rawData[i++].trim());
                int roomsCount = Integer.parseInt(rawData[i++].trim());
                spaces[k] = createSpace(roomsCount, area);
            }
            floors[j] = createFloor(spaces);
        }
        return createBuilding(floors);
    }
}
