package fr.uge.poo.visitors.expr.q4.v1;

public class StringExprVisitor implements ExprVisitor<Void>{
  private final StringBuilder builder = new StringBuilder();
  @Override
  public Void visitValue(Expr.Value value) {
    builder.append(value.value());
    return null;
  }

  @Override
  public Void visitBinOp(Expr.BinOp binOp) {
    builder.append("(");
    binOp.left().accept(this);
    builder.append(binOp.symbol());
    binOp.right().accept(this);
    builder.append(")");
    return null;
  }

  @Override
  public String toString(){
    return builder.toString();
  }
}
