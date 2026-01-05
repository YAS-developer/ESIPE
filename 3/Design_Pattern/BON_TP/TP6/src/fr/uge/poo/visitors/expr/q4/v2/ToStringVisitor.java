package fr.uge.poo.visitors.expr.q4.v2;

public class ToStringVisitor implements ExprVisitor<Void, StringBuilder> {

  public Void visitValue(Expr.Value value, StringBuilder context) {
    context.append(value.value());
    return null;
  }

  public Void visitBinOp(Expr.BinOp binOp, StringBuilder context) {
    context.append('(');
    binOp.left().accept(this, context);
    context.append(' ').append(binOp.symbol()).append(' ');
    binOp.right().accept(this, context);
    context.append(')');
    return null;
  }
}
