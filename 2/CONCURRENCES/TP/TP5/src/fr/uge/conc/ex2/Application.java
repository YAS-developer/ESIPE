package fr.uge.conc.ex2;

import java.util.List;

import com.domo.Heat4J;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        var rooms = List.of("bedroom1", "bedroom2", "kitchen", "dining-room", "bathroom", "toilets");
        var collector = new TemperatureCollector(rooms.size());

        for (String room : rooms) {
        	Thread.ofPlatform().start(() -> {
                try {
                    var temperature = Heat4J.retrieveTemperature(room);
                    collector.addTemperature(room, temperature);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread interrupted while retrieving temperature for " + room);
                }
            });
        }

        collector.waitForAllTemperatures();

        var temperatures = collector.getTemperatures();
        for (var entry : temperatures.entrySet()) {
            System.out.println("Temperature in room " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("Average temperature: " + collector.getAverageTemperature());
    }
}