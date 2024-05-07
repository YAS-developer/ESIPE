
package fr.uge.calc;

import java.util.Iterator;

public class Parser {
  public static Expr parse(Iterator<String> tokens) {
    if (!tokens.hasNext()) {
      throw new IllegalArgumentException("Expression incomplete or malformed");
    }

    String token = tokens.next();
   
  	switch (token) {
      case "+" -> new Add(parse(tokens), parse(tokens));
      case "-" -> new Sub(parse(tokens), parse(tokens));
      case "*" -> new Mul(parse(tokens), parse(tokens));
      default -> {
          try {
            int value = Integer.parseInt(token);
            return new Value(value); 
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid token: " + token);
          }
      }
    };
    throw new IllegalArgumentException("Invalid token: " + token);
  }
}

