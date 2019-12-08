package my.mapkn3.building.net.server.parallel;

import my.mapkn3.building.interfaces.Building;
import my.mapkn3.exception.BuildingUnderArrestException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialServer {
    public static void main(String[] args) {
        try {
            int serverPort = 9876;
            ServerSocket serverSocket = new ServerSocket(serverPort);
            ExecutorService executorService = Executors.newWorkStealingPool();

            while (true) {
                System.out.println("Wait for a client...");

                Socket clientSocket = serverSocket.accept();
                executorService.execute(() -> {
                    try {
                        String typeString;
                        Building building;
                        double cost;
                        ObjectInputStream socketIn = new ObjectInputStream(clientSocket.getInputStream());
                        ObjectOutputStream socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
                        try {
                            System.out.println(String.format("Client connected. %s:%d",
                                    clientSocket.getInetAddress().getHostName(), clientSocket.getPort()));
                            typeString = socketIn.readUTF();
                            System.out.println("Get type of building: " + typeString);
                            building = (Building) socketIn.readObject();
                            System.out.println("Get building: " + building);
                            cost = getCostOfBuilding(typeString, building);
                            System.out.println("Calculated cost: " + cost);
                            socketOut.writeUTF(Double.toString(cost));
                            System.out.println("Sent cost: " + cost);
                            System.out.println("Client disconnected");
                        } catch (BuildingUnderArrestException e) {
                            socketOut.writeUTF("Building under arrest");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            socketOut.close();
                            socketIn.close();
                            clientSocket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
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
}

