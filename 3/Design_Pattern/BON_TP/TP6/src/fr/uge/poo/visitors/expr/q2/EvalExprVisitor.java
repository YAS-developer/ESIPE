package fr.uge.poo.visitors.expr.q2;

public class EvalExprVisitor implements ExprVisitor{
  @Override
  public int visitValue(Expr.Value value) {
    return value.value();
  }

  @Override
  public int visitBinOp(Expr.BinOp binOp) {
    var left = binOp.left().accept(this);
    var right = binOp.right().accept(this);
    return binOp.operator().applyAsInt(left, right);
  }
}
