package fr.uge.conc.ex2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class TemperatureCollector {
    private final ConcurrentHashMap<String, Integer> temperatures;
    private final CountDownLatch latch;
    private final AtomicInteger sum;
    private final int expectedRoomCount;

    public TemperatureCollector(int roomCount) {
        this.temperatures = new ConcurrentHashMap<>();
        this.latch = new CountDownLatch(roomCount);
        this.sum = new AtomicInteger(0);
        this.expectedRoomCount = roomCount;
    }

    public void addTemperature(String room, int temperature) {
        temperatures.put(room, temperature);
        sum.addAndGet(temperature);
        latch.countDown();
    }

    public void waitForAllTemperatures() throws InterruptedException {
        latch.await();
    }

    public double getAverageTemperature() {
        return (double) sum.get() / expectedRoomCount;
    }

    public ConcurrentHashMap<String, Integer> getTemperatures() {
        return temperatures;
    }
}