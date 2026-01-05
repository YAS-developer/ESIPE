package fr.uge.poo.dragonball.training.q1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class TrainingRoom {

    public static final ExitStrategy leaveAfterTenDays = (fighter, integer) -> integer >= 10;

    // Q1: Strategy
    private final HashMap<Fighter,Integer> timeSpent = new HashMap<>();

    private final ExitStrategy leaveCondition;

    public TrainingRoom() {
      this.leaveCondition = leaveAfterTenDays;
    }

    public TrainingRoom(ExitStrategy leaveCondition) {
      Objects.requireNonNull(leaveCondition);
      this.leaveCondition = leaveCondition;
    }

  public record Fighter(String name, int power, int maxHealth){}

    /* Add a fighter to the training room */
    public void addFighter(Fighter fighter){
        Objects.requireNonNull(fighter);
        if (timeSpent.putIfAbsent(fighter,0)!=null){
            throw new IllegalStateException();
        }
    }

    /* Add a new day in the training room, the fighters that have spent 10 days
        leave the training room.
      */
    public void newDay(){
        var fightersToBeRemoved = new HashSet<Fighter>();
        for (var fighter : timeSpent.keySet()) {
            var days = timeSpent.merge(fighter, 1, Integer::sum);
            if (leaveCondition.isExiting(fighter, days)){
                fightersToBeRemoved.add(fighter);
            }
        }
        for(var fighter : fightersToBeRemoved){
            removeFighter(fighter);
        }

    }

    /* Remove a fighter from the TrainingRoom */
    private void removeFighter(Fighter fighter){
        timeSpent.remove(fighter);
    }

    @Override
    public String toString() {
        return "TrainingRoom{" +
                 timeSpent +
                '}';
    }

    public static void main(String[] args) {
        // Create a training room

        ExitStrategy exitStrategy = (fighter, days) -> days > 100 || days > fighter.power() / 100;

        TrainingRoom room = new TrainingRoom(exitStrategy);

        // Create two fighters
        TrainingRoom.Fighter goku = new TrainingRoom.Fighter("Goku", 9000, 100);
        TrainingRoom.Fighter vegeta = new TrainingRoom.Fighter("Vegeta", 8500, 100);

        // Add fighters to the training room
        room.addFighter(goku);
        room.addFighter(vegeta);

        // Simulate 10 days
        System.out.println("Simulation starts: Training Room activities");
        for (int day = 1; day <= 10; day++) {
            System.out.println("Day " + day + ":");
            room.newDay();
            System.out.println(room);
        }


    }
}
