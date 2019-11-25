package my.mapkn3.util;

import my.mapkn3.buildings.Dwelling;
import my.mapkn3.buildings.DwellingFloor;
import my.mapkn3.buildings.Flat;
import my.mapkn3.interfaces.Building;
import my.mapkn3.interfaces.Floor;
import my.mapkn3.interfaces.Space;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Buildings {
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

    private static String getStringFromBuilding(Building building) {
        List<String> data = new ArrayList<>();
        data.add(String.valueOf(building.getCountFloors()));
        for (Floor floor : building.getFloorArray()) {
            data.add(String.valueOf(floor.getCountSpace()));
            for (Space space : floor.getSpaces()) {
                data.add(String.format("%.1f %d", space.getSquare(), space.getCountRooms())
                        .replace(',', '.'));
            }
        }
        return String.join(" ", data);
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

    private static Building getBuildingFromString(String data) {
        String[] rawData = data.split(" ");
        int i = 0;
        Floor[] floors = new Floor[Integer.parseInt(rawData[i++])];
        for (int j = 0; j < floors.length; j++) {
            Space[] spaces = new Space[Integer.parseInt(rawData[i++])];
            for (int k = 0; k < spaces.length; k++) {
                spaces[k] = new Flat(Double.parseDouble(rawData[i++]), Integer.parseInt(rawData[i++]));
            }
            floors[j] = new DwellingFloor(spaces);
        }
        return new Dwelling(floors);
    }
}
