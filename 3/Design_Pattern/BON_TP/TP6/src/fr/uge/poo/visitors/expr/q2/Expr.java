package fr.uge.poo.visitors.expr.q2;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntBinaryOperator;
import java.util.regex.Pattern;

public sealed interface Expr {

//     int eval();

  int accept(ExprVisitor exprVisitor);

     record Value(int value) implements Expr {
       @Override
       public int accept(ExprVisitor exprVisitor) {
         return exprVisitor.visitValue(this);
       }
         /*@Override
         public int eval() {
             return value;
         }*/
     };

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
         public int accept(ExprVisitor exprVisitor) {
           return exprVisitor.visitBinOp(this);
         }
     }

     static Expr parse(Iterator<String> it) {
        if (!it.hasNext()) {
            throw new IllegalArgumentException("no more tokens");
        }
        String token = it.next();
        switch(token) {
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

    static void main(String[] args) {
        var iterator = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
        var expr = Expr.parse(iterator);

        var exprVisitor = new EvalExprVisitor();

        System.out.println(expr);
        System.out.println(expr.accept(exprVisitor));

    }

}
