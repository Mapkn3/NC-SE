package my.mapkn3.building.thread;

import my.mapkn3.building.interfaces.Floor;

public class Repairer extends Thread {
    private Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.getSpaceCount(); i++) {
            if (this.isInterrupted()) {
                return;
            }
            System.out.println(String.format(
                    "Repairing space number %d with total area %.1f square meters",
                    i,
                    floor.getSpace(i).getArea()));
        }
    }
}
