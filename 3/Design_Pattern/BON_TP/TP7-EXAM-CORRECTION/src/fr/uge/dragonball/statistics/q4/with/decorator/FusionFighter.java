package fr.uge.dragonball.statistics.q4.with.decorator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Q4: Composite
public record FusionFighter(List<Fighter> fighters) implements Fighter {

  public FusionFighter(List<Fighter> fighters) {
    Objects.requireNonNull(fighters);
    this.fighters = List.copyOf(fighters);
  }

  @Override
  public String name() {
    return fighters.stream().map(Fighter::name).collect(Collectors.joining("-","<",">"));
  }

  @Override
  public int power() {
    return fighters.stream().mapToInt(Fighter::power).sum();
  }

  @Override
  public int maxHealth() {
    return fighters.stream().mapToInt(Fighter::maxHealth).min().orElseThrow();
  }
}
