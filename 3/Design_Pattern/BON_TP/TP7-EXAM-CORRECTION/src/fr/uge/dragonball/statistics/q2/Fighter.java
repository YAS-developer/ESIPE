package fr.uge.dragonball.statistics.q2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Fighter(String name, int power, int maxHealth) {

  public Fighter {
    Objects.requireNonNull(name);
    if (power < 0) {
      throw new IllegalArgumentException();
    }
    if (maxHealth < 0) {
      throw new IllegalArgumentException();
    }
  }

  // Pattern: Factory
  static Fighter fusion(List<Fighter> fighters) {
    Objects.requireNonNull(fighters);

    if (fighters.size() < 2) {
      throw new IllegalArgumentException();
    }

    var name = fighters.stream().map(Fighter::name).collect(Collectors.joining("-","<",">"));
    var power = fighters.stream().mapToInt(Fighter::power).sum();
    var maxHealth = fighters.stream().mapToInt(Fighter::maxHealth).min().orElseThrow();

    return new Fighter(name, power, maxHealth);
  }

  Fighter transformation(Transformation transformation) {
    var name = this.name + " as " + transformation.name();
    var power = transformation.power().applyAsInt(this);
    var maxHealth = transformation.health().applyAsInt(this);
    return new Fighter(name, power, maxHealth);
  }

}
