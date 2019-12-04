package my.mapkn3.building.thread;

import my.mapkn3.building.interfaces.Floor;

import java.util.concurrent.Semaphore;

public class SequentialCleaner implements Runnable {
    private final Semaphore semaphore;
    private final Floor floor;

    public SequentialCleaner(Floor floor, Semaphore semaphore) {
        this.semaphore = semaphore;
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.getSpaceCount(); i++) {
            try {
                semaphore.acquire();
                synchronized (floor) {
                    System.out.println(String.format(
                            "Sequential  cleaning room number %d with total area %.1f square meters",
                            i,
                            floor.getSpace(i).getArea()));
                }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
