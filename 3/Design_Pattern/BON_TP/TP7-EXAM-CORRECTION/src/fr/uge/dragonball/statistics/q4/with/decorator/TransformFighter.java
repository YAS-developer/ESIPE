package fr.uge.dragonball.statistics.q4.with.decorator;

import java.util.Objects;

public record TransformFighter(Fighter fighter, Transformation transformation) implements Fighter {

  public TransformFighter {
    Objects.requireNonNull(fighter);
    Objects.requireNonNull(transformation);
  }

  @Override
  public String name() {
    return fighter.name() + " as " + transformation.name();
  }

  @Override
  public int power() {
    return transformation.power().applyAsInt(fighter);
  }

  @Override
  public int maxHealth() {
    return transformation.health().applyAsInt(fighter);
  }
}
