package fr.uge.dragonball.statistics.q4.with.decorator;

import java.util.ArrayList;
import java.util.List;

public sealed interface Fighter permits BaseFighter, FusionFighter, TransformFighter {

  String name();

  int power();

  int maxHealth();

  static List<Fighter> baseFighters(Fighter fighter) {
    return switch (fighter) {
      case BaseFighter baseFighter -> List.of(baseFighter);
      case TransformFighter transformFighter -> baseFighters(transformFighter.fighter());
      case FusionFighter fusionFighter -> {
        var list = new ArrayList<Fighter>();
        for(var f : fusionFighter.fighters()) {
          list.addAll(baseFighters(f));
        }
        yield list;
      }
    };
  }

}
