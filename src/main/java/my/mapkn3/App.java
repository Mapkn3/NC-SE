package my.mapkn3;

import my.mapkn3.building.dwelling.Dwelling;
import my.mapkn3.building.interfaces.Building;
import my.mapkn3.util.Buildings;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        try {
            Building dwelling = new Dwelling(3, new int[] {2, 1, 3});
            StringWriter stringWriter = new StringWriter();
            Buildings.writeBuilding(dwelling, stringWriter);
            String dwellingStr = stringWriter.toString();
            System.out.println(dwellingStr);
            Building restoreDwelling = Buildings.readBuilding(new StringReader(dwellingStr));
            Buildings.outputBuilding(restoreDwelling, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
