package my.mapkn3;

import my.mapkn3.building.dwelling.Dwelling;
import my.mapkn3.building.dwelling.hotel.Hotel;
import my.mapkn3.building.interfaces.Building;
import my.mapkn3.building.interfaces.Floor;
import my.mapkn3.building.office.Office;
import my.mapkn3.building.office.OfficeBuilding;
import my.mapkn3.building.office.OfficeFloor;
import my.mapkn3.building.thread.Cleaner;
import my.mapkn3.building.thread.Repairer;
import my.mapkn3.building.thread.SequentialCleaner;
import my.mapkn3.building.thread.SequentialRepairer;
import my.mapkn3.util.Buildings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.Semaphore;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");
        try {
            Building dwelling = new Dwelling(3, new int[]{2, 1, 3});
            StringWriter stringWriter = new StringWriter();
            Buildings.writeBuilding(dwelling, stringWriter);
            String dwellingStr = stringWriter.toString();
            System.out.println(dwellingStr);
            Building restoreDwelling = Buildings.readBuilding(new StringReader(dwellingStr), OfficeBuilding.class, OfficeFloor.class, Office.class);
            Buildings.outputBuilding(restoreDwelling, System.out);
            System.out.println(dwelling.toString());
            System.out.println(restoreDwelling.toString());
            System.out.println();


            Floor floor = new OfficeFloor(20);
            Repairer repairer = new Repairer(floor);
            Cleaner cleaner = new Cleaner(floor);
            repairer.start();
            cleaner.start();
            Thread.sleep(1);
            repairer.interrupt();
            Thread.sleep(1);
            cleaner.interrupt();

            Semaphore semaphore = new Semaphore(1, true);
            Thread sequentialRepairerThread = new Thread(new SequentialRepairer(floor, semaphore));
            Thread sequentialCleanerThread = new Thread(new SequentialCleaner(floor, semaphore));
            sequentialRepairerThread.setPriority(Thread.MIN_PRIORITY);
            sequentialCleanerThread.setPriority(Thread.MAX_PRIORITY);
            sequentialRepairerThread.start();
            sequentialCleanerThread.start();

            try (FileOutputStream buildingOutput = new FileOutputStream("buildings.txt");
                 FileOutputStream typeOutput = new FileOutputStream("types.txt");) {
                Building dwellingForFile = new Dwelling(3, new int[]{2, 1, 3});
                Building officeForFile = new OfficeBuilding(3, new int[]{2, 1, 3});
                Building hotelForFile = new Hotel(3, new int[]{2, 1, 3});

                Buildings.outputBuilding(dwellingForFile, buildingOutput);
                Buildings.outputBuilding(officeForFile, buildingOutput);
                Buildings.outputBuilding(hotelForFile, buildingOutput);
                typeOutput.write("dwelling\n".getBytes());
                typeOutput.write("office\n".getBytes());
                typeOutput.write("hotel\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
