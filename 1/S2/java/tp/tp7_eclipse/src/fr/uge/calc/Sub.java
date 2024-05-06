// fr/uge/calc/Sub.java
package fr.uge.calc;

public record Sub(Expr left, Expr right) implements Expr {
    @Override
    public int eval() {
        return left.eval() - right.eval();
    }
    
    @Override
    public String toString() {
      var sb = new StringBuilder();
      sb.append('(').append(left).append(" + ").append(right).append(")");
       return (sb.append('(').append(left).append(" - ").append(right).append(")")).toString();
    }
}

