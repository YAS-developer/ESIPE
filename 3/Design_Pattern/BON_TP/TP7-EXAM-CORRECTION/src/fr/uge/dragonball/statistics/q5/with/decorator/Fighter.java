package fr.uge.dragonball.statistics.q5.with.decorator;

public sealed interface Fighter permits BaseFighter,
        FusionFighter,
        TransformFighter {

  String name();

  int power();

  int maxHealth();

  <T> T accept(FighterVisitor<T> visitor);

}
