package fr.uge.poo.dragonball.training.my.q2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class TrainingRoom {

  private static final ExitStrategy leaveAfterTenDays = (fighter, integer) -> integer >= 10;

  // Q1: Strategy
  // Q2: Observer
  private final HashMap<Fighter, Integer> timeSpent = new HashMap<>();

  private final ExitStrategy leaveCondition;

  private final List<TrainingRoomObserver> observers;

  public TrainingRoom() {
    leaveCondition = leaveAfterTenDays;
    observers = new ArrayList<>();
  }

  public TrainingRoom(ExitStrategy leaveCondition, List<TrainingRoomObserver> observers) {
    Objects.requireNonNull(leaveCondition);
    this.leaveCondition = leaveCondition;
    this.observers = List.copyOf(observers);
  }

  static void main(String[] args) {
    // Create a training room

    ExitStrategy exitStrategy = (fighter, days) -> days > 100 || days > fighter.power() / 100;

    var facebookObserver = new Observers.FacebookObserver();
    var instagramObserver = new InstagramObserver();
    var mailObserver = new Observers.MailObserver("arnaud.carayol@univ-eiffel.fr", value -> value >= 900);

    TrainingRoom room = new TrainingRoom(exitStrategy, List.of(facebookObserver, instagramObserver, mailObserver));

    // Create two fighters
    Fighter goku = new Fighter("Goku", 9000, 100);
    Fighter vegeta = new Fighter("Vegeta", 8500, 100);

    // Add fighters to the training room
    room.addFighter(goku);
    room.addFighter(vegeta);

    // Simulate 10 days
    System.out.println("Simulation starts: Training Room activities");
    for (int day = 1; day <= 105; day++) {
      System.out.println("Day " + day + ":");
      room.newDay();
      System.out.println(room);
    }


  }

  public List<Fighter> fighters() {
    return List.copyOf(timeSpent.keySet());
  }

  /* Add a fighter to the training room */
  public void addFighter(Fighter fighter) {
    Objects.requireNonNull(fighter);
    if (timeSpent.putIfAbsent(fighter, 0) != null) {
      throw new IllegalStateException();
    }
    notifyNewFighter(fighter);
  }

  private void notifyNewFighter(Fighter fighter) {
    for (var observer : observers) {
      observer.onNewFighter(fighter);
    }
  }

  /* Add a new day in the training room, the fighters that have spent 10 days
      leave the training room.
    */
  public void newDay() {
    var fightersToBeRemoved = new HashSet<Fighter>();
    for (var fighter : timeSpent.keySet()) {
      var days = timeSpent.merge(fighter, 1, Integer::sum);
      if (leaveCondition.isExiting(fighter, days)) {
        fightersToBeRemoved.add(fighter);
      }
    }
    for (var fighter : fightersToBeRemoved) {
      removeFighter(fighter);
    }
    notifyNewDay();
  }

  private void notifyNewDay() {
    for (var observer : observers) {
      observer.newDay();
    }
  }

  /* Remove a fighter from the TrainingRoom */
  private void removeFighter(Fighter fighter) {
    timeSpent.remove(fighter);
    notifyRemoveFighter(fighter);
  }

  private void notifyRemoveFighter(Fighter fighter) {
    for (var observer : observers) {
      observer.onLeaveFighter(fighter);
    }
  }

  @Override
  public String toString() {
    return "TrainingRoom{" + timeSpent + '}';
  }

  public record Fighter(String name, int power, int maxHealth) {
  }
}
