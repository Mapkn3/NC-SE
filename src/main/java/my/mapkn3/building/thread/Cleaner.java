package my.mapkn3.building.thread;

import my.mapkn3.building.interfaces.Floor;

public class Cleaner extends Thread {
    private Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.getSpaceCount(); i++) {
            if (this.isInterrupted()) {
                return;
            }
            System.out.println(String.format(
                    "Cleaning room number %d with total area %.1f square meters",
                    i,
                    floor.getSpace(i).getArea()));
        }
    }
}
