// fr/uge/calc/BinOp.java
package fr.uge.calc;

public abstract class BinOp implements Expr {
    protected final Expr left;
    protected final Expr right;

    protected BinOp(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int eval() {
        return apply(left.eval(), right.eval());
    }

    protected abstract int apply(int leftValue, int rightValue);
}
