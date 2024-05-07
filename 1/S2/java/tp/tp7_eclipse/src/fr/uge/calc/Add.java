package fr.uge.calc;

public record Add(Expr left, Expr right) implements BinOp {
  @Override
  public Expr left() {
      return left;
  }

  @Override
  public Expr right() {
      return right;
  }

  @Override
  public int applyOp(int left, int right) {
      return left + right;
  }

  @Override
  public String toString() {
      return operationToString("+");
  }
}
