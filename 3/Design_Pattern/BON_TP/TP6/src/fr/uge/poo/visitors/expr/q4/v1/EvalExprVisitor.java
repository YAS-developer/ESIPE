package fr.uge.poo.visitors.expr.q4.v1;

public class EvalExprVisitor implements ExprVisitor<Integer>{
  @Override
  public Integer visitValue(Expr.Value value) {
    return value.value();
  }

  @Override
  public Integer visitBinOp(Expr.BinOp binOp) {
    return binOp.operator().applyAsInt(binOp.left().accept(this), binOp.right().accept(this));
  }
}
