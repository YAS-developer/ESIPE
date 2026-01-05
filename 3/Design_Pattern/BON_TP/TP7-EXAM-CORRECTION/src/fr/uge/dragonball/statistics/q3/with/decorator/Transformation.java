package fr.uge.dragonball.statistics.q3.with.decorator;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class Transformation {
  private static final HashMap<String, Transformation> TRANSFORMATIONS = new HashMap<>();

  private final String name;
  private final ToIntFunction<Fighter> power;
  private final ToIntFunction<Fighter> health;


  private Transformation(String name, ToIntFunction<Fighter> power, ToIntFunction<Fighter> health) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(power);
    Objects.requireNonNull(health);

    this.name = name;
    this.power = power;
    this.health = health;
  }

  public String name() {
    return name;
  }

  public ToIntFunction<Fighter> power() {
    return power;
  }

  public ToIntFunction<Fighter> health() {
    return health;
  }

  // Q3: Singleton
  public static Transformation of(String name, ToIntFunction<Fighter> power, ToIntFunction<Fighter> health) {
    return TRANSFORMATIONS.computeIfAbsent(name, s -> new Transformation(name, power, health));
  }
}
