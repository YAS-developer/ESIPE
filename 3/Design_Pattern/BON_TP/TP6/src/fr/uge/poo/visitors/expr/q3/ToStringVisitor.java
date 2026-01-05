package fr.uge.poo.visitors.expr.q3;

public class ToStringVisitor implements ExprVisitor<String> {


  public String visitValue(Expr.Value value) {
    return value.value() + "";
  }

  public String visitBinOp(Expr.BinOp binOp) {
    var left = binOp.left().accept(this);
    var right = binOp.right().accept(this);
    return "(" + left + " " + binOp.symbol() + " " + right + ")";
  }
}
