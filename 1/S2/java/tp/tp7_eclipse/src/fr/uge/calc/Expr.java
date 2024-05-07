package fr.uge.calc;


public sealed interface Expr permits Value, BinOp {
  int eval();
}
