package fr.uge.poo.visitors.expr.q3;

public class EvalExprVisitor implements ExprVisitor<Integer> {

  @Override
  public Integer visitValue(Expr.Value value) {
    return value.value();
  }

  @Override
  public Integer visitBinOp(Expr.BinOp binOp) {
    var left = binOp.left().accept(this);
    var right = binOp.right().accept(this);
    return binOp.operator().applyAsInt(left, right);
  }
}
