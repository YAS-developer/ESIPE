package fr.uge.poo.dragonball.training.my.q2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.IntPredicate;

public final class Observers {

  private Observers() {
  }

  private static abstract class ObserverWithList {

    final List<TrainingRoom.Fighter> fighters = new ArrayList<>();

    public void onNewFighter(TrainingRoom.Fighter fighter) {
      Objects.requireNonNull(fighter);
      fighters.add(fighter);
    }

    public void onLeaveFighter(TrainingRoom.Fighter fighter) {
      Objects.requireNonNull(fighter);
      fighters.remove(fighter);
    }

  }

  public static class FacebookObserver extends Observers.ObserverWithList implements TrainingRoomObserver {
    @Override
    public void onNewFighter(TrainingRoom.Fighter fighter) {
      var maxPower = fighters.stream().mapToInt(TrainingRoom.Fighter::power).max().orElse(0);
      if (fighter.power() > maxPower) {
        System.out.println("New Fighter: " + fighter);
      }
      super.onNewFighter(fighter);
    }

    @Override
    public void newDay() {

    }
  }

  public static class MailObserver extends Observers.ObserverWithList implements TrainingRoomObserver{

    private final String mail;
    private final IntPredicate scoreCondition;

    public MailObserver(String mail, IntPredicate scoreCondition) {
      this.mail = mail;
      this.scoreCondition = scoreCondition;
    }

    @Override
    public void newDay() {
      var score = fighters.stream().mapToInt(TrainingRoom.Fighter::power).sum();
      if (scoreCondition.test(score)) {
        System.out.println("Send mail to: " + mail + " content: It is over");
      }
    }
  }

}
