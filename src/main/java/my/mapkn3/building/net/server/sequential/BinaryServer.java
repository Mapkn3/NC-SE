package my.mapkn3.building.net.server.sequential;

import my.mapkn3.building.factory.DwellingFactory;
import my.mapkn3.building.factory.HotelFactory;
import my.mapkn3.building.factory.OfficeFactory;
import my.mapkn3.building.factory.interfaces.BuildingFactory;
import my.mapkn3.building.interfaces.Building;
import my.mapkn3.exception.BuildingUnderArrestException;
import my.mapkn3.util.Buildings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class BinaryServer {
    private static DwellingFactory dwellingFactory;
    private static OfficeFactory officeFactory;
    private static HotelFactory hotelFactory;

    public static void main(String[] args) {
        try {
            int serverPort = 9876;
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (true) {
                System.out.println("Wait for a client...");

                String typeString;
                String buildingString;
                Building building;
                double cost;
                BuildingFactory factory;
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream socketIn = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
                try {
                    System.out.println(String.format("Client connected. %s:%d",
                            clientSocket.getInetAddress().getHostName(), clientSocket.getPort()));
                    typeString = socketIn.readUTF();
                    System.out.println("Get type of building: " + typeString);
                    buildingString = socketIn.readUTF();
                    System.out.println("Get building: " + buildingString);
                    factory = getBuildingFactoryForType(typeString);
                    cost = 0;
                    if (factory != null) {
                        Buildings.setBuildingFactory(factory);
                        building = Buildings.readBuilding(new StringReader(buildingString));
                        cost = getCostOfBuilding(typeString, building);
                    }
                    System.out.println("Calculated cost: " + cost);
                    socketOut.writeUTF(Double.toString(cost));
                    System.out.println("Sent cost: " + cost);
                    System.out.println("Client disconnected");
                } catch (BuildingUnderArrestException e) {
                    socketOut.writeUTF("Building under arrest");
                } finally {
                    socketOut.close();
                    socketIn.close();
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double getCostOfBuilding(String type, Building building) throws BuildingUnderArrestException {
        boolean isBuildingUnderArrest = isUnderArrest();
        if (isBuildingUnderArrest) {
            throw new BuildingUnderArrestException();
        }
        double multiplier = getMultiplierForTypeOfBuilding(type);
        return building.getTotalArea() * multiplier;
    }

    private static boolean isUnderArrest() {
        Random random = new Random();
        double result = random.nextDouble();
        return result < 0.1;
    }

    private static double getMultiplierForTypeOfBuilding(String type) {
        double multiplier = 0;
        if (type.equalsIgnoreCase("dwelling")) {
            multiplier = 1000;
        }
        if (type.equalsIgnoreCase("office")) {
            multiplier = 1500;
        }
        if (type.equalsIgnoreCase("hotel")) {
            multiplier = 2000;
        }
        return multiplier;
    }

    private static BuildingFactory getBuildingFactoryForType(String type) {
        BuildingFactory factory = null;
        if (type.equalsIgnoreCase("dwelling")) {
            factory = getDwellingFactory();
        }
        if (type.equalsIgnoreCase("office")) {
            factory = getOfficeFactory();
        }
        if (type.equalsIgnoreCase("hotel")) {
            factory = getHotelFactory();
        }
        return factory;
    }

    private static DwellingFactory getDwellingFactory() {
        if (dwellingFactory == null) {
            dwellingFactory = new DwellingFactory();
        }
        return dwellingFactory;
    }

    private static OfficeFactory getOfficeFactory() {
        if (officeFactory == null) {
            officeFactory = new OfficeFactory();
        }
        return officeFactory;
    }

    private static HotelFactory getHotelFactory() {
        if (hotelFactory == null) {
            hotelFactory = new HotelFactory();
        }
        return hotelFactory;
    }
}
