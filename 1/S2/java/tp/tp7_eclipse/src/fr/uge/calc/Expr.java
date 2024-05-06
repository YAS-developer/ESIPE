package fr.uge.calc;


public sealed interface Expr permits Value, BinOp {

    public int eval();
}
