package my.mapkn3.util;

import my.mapkn3.building.SynchronizedFloor;
import my.mapkn3.building.factory.DwellingFactory;
import my.mapkn3.building.factory.interfaces.BuildingFactory;
import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.interfaces.Space;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    public static Space createSpace(double area, int roomsCount) {
        return buildingFactory.createSpace(area, roomsCount);
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

    public static <S extends Space> S createSpace(double area, Class<S> spaceClass) {
        try {
            Constructor<S> constructor = spaceClass.getConstructor(double.class);
            return constructor.newInstance(area);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static <S extends Space> S createSpace(double area, int roomsCount, Class<S> spaceClass) {
        try {
            Constructor<S> constructor = spaceClass.getConstructor(double.class, int.class);
            return constructor.newInstance(area, roomsCount);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static <F extends Floor> F createFloor(int spacesCount, Class<F> floorClass) {
        try {
            Constructor<F> constructor = floorClass.getConstructor(int.class);
            return constructor.newInstance(spacesCount);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static <F extends Floor> F createFloor(Space[] spaces, Class<F> floorClass) {
        try {
            Constructor<F> constructor = floorClass.getConstructor(Space[].class);
            return constructor.newInstance((Object) spaces);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static <B extends Building> B createBuilding(int floorsCount, int[] spacesCounts, Class<B> buildingClass) {
        try {
            Constructor<B> constructor = buildingClass.getConstructor(int.class, int[].class);
            return constructor.newInstance(floorsCount, spacesCounts);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static <B extends Building> B createBuilding(Floor[] floors, Class<B> buildingClass) {
        try {
            Constructor<B> constructor = buildingClass.getConstructor(Floor[].class);
            return constructor.newInstance((Object) floors);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public Floor synchronizedFloor(Floor floor) {
        return new SynchronizedFloor(floor);
    }

    public static void outputBuilding(Building building, OutputStream out) throws IOException {
        String data = getStringFromBuilding(building);
        out.write(data.getBytes());
        out.write('\n');
        out.flush();
    }

    public static Building inputBuilding(InputStream in) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        String buildingString = dataInputStream.readUTF();
        return getBuildingFromString(buildingString);
    }

    public static void writeBuilding(Building building, Writer out) throws IOException {
        String data = getStringFromBuilding(building);
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.print(data);
        printWriter.print('\n');
        printWriter.flush();
    }

    public static Building readBuilding(Reader in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(in);
        String buildingString = bufferedReader.readLine();
        return getBuildingFromString(buildingString);
    }

    public static <B extends Building, F extends Floor, S extends Space> B inputBuilding(
            InputStream in, Class<B> buildingClass, Class<F> floorClass, Class<S> spaceClass) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        String buildingString = dataInputStream.readUTF();
        return getBuildingFromString(buildingString, buildingClass, floorClass, spaceClass);
    }

    public static <B extends Building, F extends Floor, S extends Space> B  readBuilding(
            Reader in, Class<B> buildingClass, Class<F> floorClass, Class<S> spaceClass) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(in);
        String buildingString = bufferedReader.readLine();
        return getBuildingFromString(buildingString, buildingClass, floorClass, spaceClass);
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
                spaces[k] = createSpace(area, roomsCount);
            }
            floors[j] = createFloor(spaces);
        }
        return createBuilding(floors);
    }

    private static <B extends Building, F extends Floor, S extends Space> B getBuildingFromString(
            String data, Class<B> buildingClass, Class<F> floorClass, Class<S> spaceClass) {
        String[] rawData = data.split(" ");
        int i = 0;
        Floor[] floors = new Floor[Integer.parseInt(rawData[i++])];
        for (int j = 0; j < floors.length; j++) {
            Space[] spaces = new Space[Integer.parseInt(rawData[i++])];
            for (int k = 0; k < spaces.length; k++) {
                double area = Double.parseDouble(rawData[i++].trim());
                int roomsCount = Integer.parseInt(rawData[i++].trim());
                spaces[k] = createSpace(area, roomsCount, spaceClass);
            }
            floors[j] = createFloor(spaces, floorClass);
        }
        return createBuilding(floors, buildingClass);
    }
}
