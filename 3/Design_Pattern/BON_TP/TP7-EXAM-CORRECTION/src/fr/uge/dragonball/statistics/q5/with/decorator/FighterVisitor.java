package fr.uge.dragonball.statistics.q5.with.decorator;

public interface FighterVisitor<T> {

  T visit(BaseFighter baseFighter);
  T visit(TransformFighter transformFighter);
  T visit(FusionFighter fusionFighter);

}
