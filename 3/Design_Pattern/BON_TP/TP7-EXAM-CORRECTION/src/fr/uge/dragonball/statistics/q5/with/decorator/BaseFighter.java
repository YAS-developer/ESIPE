package fr.uge.dragonball.statistics.q5.with.decorator;

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

  @Override
  public <T> T accept(FighterVisitor<T> visitor) {
    return visitor.visit(this);
  }
}
