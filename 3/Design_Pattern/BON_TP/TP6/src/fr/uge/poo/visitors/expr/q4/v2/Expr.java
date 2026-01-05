package fr.uge.poo.visitors.expr.q4.v2;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntBinaryOperator;
import java.util.regex.Pattern;

public sealed interface Expr {

//     int eval();

  ExprVisitor<Void, StringBuilder> TO_STRING_VISITOR = new ToStringVisitor();
  ExprVisitor<Integer, Void> EVAL_EXPR_VISITOR = new EvalExprVisitor();

  static Expr parse(Iterator<String> it) {
    if (!it.hasNext()) {
      throw new IllegalArgumentException("no more tokens");
    }
    String token = it.next();
    switch (token) {
      case "+":
        return new BinOp(parse(it), parse(it), token, Integer::sum);
      case "-":
        return new BinOp(parse(it), parse(it), token, (a, b) -> a - b);
      case "*":
        return new BinOp(parse(it), parse(it), token, (a, b) -> a * b);
      case "/":
        return new BinOp(parse(it), parse(it), token, (a, b) -> a / b);
      default:
        return new Value(Integer.parseInt(token));
    }
  }

  static void main() {
    var iterator = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
    var expr = Expr.parse(iterator);

    var sb = new StringBuilder();
    expr.accept(TO_STRING_VISITOR, sb);
    System.out.println(sb);
    System.out.println(expr.accept(EVAL_EXPR_VISITOR, null));
  }

  <T, C> T accept(ExprVisitor<T, C> exprVisitor, C context);

  record Value(int value) implements Expr {
    @Override
    public <T, C> T accept(ExprVisitor<T, C> exprVisitor, C context) {
      return exprVisitor.visitValue(this, context);
    }
         /*@Override
         public int eval() {
             return value;
         }*/
  }

  record BinOp(Expr left, Expr right, String symbol, IntBinaryOperator operator) implements Expr {
    public BinOp {
      Objects.requireNonNull(left);
      Objects.requireNonNull(right);
      Objects.requireNonNull(symbol);
      Objects.requireNonNull(operator);
    }

    /*@Override
    public int eval() {
        return operator.applyAsInt(left.eval(), right.eval());
    }*/
    @Override
    public <T, C> T accept(ExprVisitor<T, C> exprVisitor, C context) {
      return exprVisitor.visitBinOp(this, context);
    }
  }

}
