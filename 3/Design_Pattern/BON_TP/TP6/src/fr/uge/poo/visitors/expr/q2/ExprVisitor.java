package fr.uge.poo.visitors.expr.q2;

public interface ExprVisitor {
    int visitValue(Expr.Value value);
    int visitBinOp(Expr.BinOp binOp);
}
