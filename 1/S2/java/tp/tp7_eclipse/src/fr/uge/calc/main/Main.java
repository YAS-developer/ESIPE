package fr.uge.calc.main;

import fr.uge.calc.Add;
import fr.uge.calc.Expr;
import fr.uge.calc.Sub;
import fr.uge.calc.Value;

public class Main {
    public static void main(String[] args) {
//        Expr expression = new Add(new Value(2), new Value(3));
//        Expr expression2 = new Sub(new Mul(new Value(2), new Value(3)), new Value(4));

      Expr expression = new Add(new Value(2), new Value(3));
      Expr expression2 = new Sub(new Add(new Value(2), new Value(3)), new Value(4));
      System.out.println(expression2.eval());
    }
}
