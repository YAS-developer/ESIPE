package fr.uge.poo.dragonball.training.q1;


@FunctionalInterface
public interface ExitStrategy {

  boolean isExiting(TrainingRoom.Fighter fighter, int days);

}
