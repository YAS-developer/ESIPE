package fr.uge.dragonball.statistics.q2.with.decorator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Fighter {

  String name();

  int power();

  int maxHealth();

  // Pattern: Factory
  static Fighter fusion(List<Fighter> fighters) {
    Objects.requireNonNull(fighters);

    if (fighters.size() < 2) {
      throw new IllegalArgumentException();
    }

    var name = fighters.stream().map(Fighter::name).collect(Collectors.joining("-","<",">"));
    var power = fighters.stream().mapToInt(Fighter::power).sum();
    var maxHealth = fighters.stream().mapToInt(Fighter::maxHealth).min().orElseThrow();

    return new BaseFighter(name, power, maxHealth);
  }


}
