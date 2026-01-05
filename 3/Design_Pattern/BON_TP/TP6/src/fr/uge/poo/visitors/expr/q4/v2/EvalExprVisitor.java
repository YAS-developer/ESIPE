package fr.uge.poo.visitors.expr.q4.v2;

public class EvalExprVisitor implements ExprVisitor<Integer, Void> {


  @Override
  public Integer visitValue(Expr.Value value, Void notUse) {
    return value.value();
  }

  @Override
  public Integer visitBinOp(Expr.BinOp binOp, Void notUse) {
    var left = binOp.left().accept(this, notUse);
    var right = binOp.right().accept(this, notUse);
    return binOp.operator().applyAsInt(left, right);
  }
}
