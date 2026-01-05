package fr.uge.poo.visitors.expr.q4.v1;

public interface ExprVisitor<T> {
    public T visitValue(Expr.Value value);
    public T visitBinOp(Expr.BinOp binOp);
}
