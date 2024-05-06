package fr.uge.calc;

public sealed interface Expr permits Value, Add, Sub, Mul{
  public int eval();
}
