package fr.uge.poo.dragonball.training.my.q2;


@FunctionalInterface
public interface ExitStrategy {

  boolean isExiting(TrainingRoom.Fighter fighter, int days);

}
