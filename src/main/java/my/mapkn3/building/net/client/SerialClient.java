package my.mapkn3.building.net.client;

import my.mapkn3.building.factory.DwellingFactory;
import my.mapkn3.building.factory.HotelFactory;
import my.mapkn3.building.factory.OfficeFactory;
import my.mapkn3.building.factory.interfaces.BuildingFactory;
import my.mapkn3.building.interfaces.Building;
import my.mapkn3.util.Buildings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class SerialClient {
    private static DwellingFactory dwellingFactory;
    private static OfficeFactory officeFactory;
    private static HotelFactory hotelFactory;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalStateException("You should use 3 parameters:\n" +
                    "1) Name of file with buildings.\n" +
                    "2) Name of file with type of buildings.\n" +
                    "3) Name for result file with cost of buildings.");
        }

        String buildingsFileName = args[0];
        String typesFileName = args[1];
        String costsFileName = args[2];
        checkFileNamesOrThrow(buildingsFileName, typesFileName);
        System.out.println(String.format("File from params:\n  %s\n  %s\n  %s",
                buildingsFileName, typesFileName, costsFileName));

        try (BufferedReader buildingReader = createBufferedReaderForFile(buildingsFileName);
             BufferedReader typesReader = createBufferedReaderForFile(typesFileName);
             BufferedWriter costsWriter = createBufferedWriterForFile(costsFileName)) {

            int serverPort = 9876;
            InetAddress serverHost = InetAddress.getLocalHost();
            String buildingString;
            String typeString;
            String cost;
            BuildingFactory factory;
            Building building;
            while ((buildingString = buildingReader.readLine()) != null &&
                    (typeString = typesReader.readLine()) != null) {
                try (Socket socket = new Socket(serverHost, serverPort);
                     ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream())) {
                    System.out.println(String.format("Connect to server %s:%d", serverHost.getHostName(), serverPort));
                    System.out.println(String.format("Read %s building: %s", typeString, buildingString));
                    factory = getBuildingFactoryForType(typeString);
                    Buildings.setBuildingFactory(factory);
                    System.out.println("Send to server type: " + typeString);
                    socketOut.writeUTF(typeString);
                    socketOut.flush();
                    building = Buildings.readBuilding(new StringReader(buildingString));
                    System.out.println("Send to server building: " + building.toString());
                    socketOut.writeObject(building);
                    socketOut.flush();

                    cost = socketIn.readUTF();
                    System.out.println("Receive from server: " + cost);
                    costsWriter.write(cost);
                    costsWriter.newLine();
                    System.out.println("Disconnect from server");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedReader createBufferedReaderForFile(String fileName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileName));
    }

    private static BufferedWriter createBufferedWriterForFile(String fileName) throws IOException {
        return new BufferedWriter(new FileWriter(fileName));
    }

    private static void checkFileNamesOrThrow(String... fileNames) throws IllegalStateException {
        Arrays.stream(fileNames).forEach(SerialClient::checkFileNameOrThrow);
    }

    private static void checkFileNameOrThrow(String fileName) throws IllegalStateException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IllegalStateException(String.format("File with name \"%s\" not exist.", fileName));
        }
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
        if (factory == null) {
            throw new IllegalStateException("Unknown type of building");
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

