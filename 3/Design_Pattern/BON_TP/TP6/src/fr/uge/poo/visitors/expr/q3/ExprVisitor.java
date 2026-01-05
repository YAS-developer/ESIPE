package fr.uge.poo.visitors.expr.q3;

public interface ExprVisitor<T> {
    T visitValue(Expr.Value value);
    T visitBinOp(Expr.BinOp binOp);

}
