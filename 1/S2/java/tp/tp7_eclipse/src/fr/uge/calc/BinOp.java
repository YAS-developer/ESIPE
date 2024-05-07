package fr.uge.calc;


public sealed interface BinOp extends Expr permits Add, Sub, Mul {
	Expr left();

  Expr right();

  int applyOp(int left, int right);

  @Override
  default int eval() {
    return applyOp(left().eval(), right().eval());
  }
  
  default String operationToString(String operator) {
    var sb = new StringBuilder();
    return sb.append("(").append(left()).append(" ").append(operator).append(" ").append(right()).append(")").toString();
  }
  
}