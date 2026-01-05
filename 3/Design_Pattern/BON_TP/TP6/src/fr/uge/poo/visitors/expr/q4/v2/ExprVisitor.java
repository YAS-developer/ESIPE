package fr.uge.poo.visitors.expr.q4.v2;

public interface ExprVisitor<T, C> {
    T visitValue(Expr.Value value, C context);
    T visitBinOp(Expr.BinOp binOp, C context);

}
