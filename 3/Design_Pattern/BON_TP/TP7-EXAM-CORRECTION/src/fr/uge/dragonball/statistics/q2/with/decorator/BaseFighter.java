package fr.uge.dragonball.statistics.q2.with.decorator;

import java.util.Objects;

public record BaseFighter(String name, int power, int maxHealth) implements Fighter {

  public BaseFighter {
    Objects.requireNonNull(name);
    if (power < 0) {
      throw new IllegalArgumentException();
    }

    if (maxHealth < 0) {
      throw new IllegalArgumentException();
    }
  }


  BaseFighter transformation(Transformation transformation) {
    var name = this.name + " as " + transformation.name();
    var power = transformation.power().applyAsInt(this);
    var maxHealth = transformation.health().applyAsInt(this);
    return new BaseFighter(name, power, maxHealth);
  }

}
