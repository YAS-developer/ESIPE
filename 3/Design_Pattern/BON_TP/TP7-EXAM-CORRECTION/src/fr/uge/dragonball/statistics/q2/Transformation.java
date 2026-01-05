package fr.uge.dragonball.statistics.q2;

import java.util.Objects;
import java.util.function.ToIntFunction;

public record Transformation(String name, ToIntFunction<Fighter> power, ToIntFunction<Fighter> health) {
  public Transformation {
    Objects.requireNonNull(name);
    Objects.requireNonNull(power);
    Objects.requireNonNull(health);
  }
}
