package fr.uge.candy;

public sealed interface Treat permits Candy, CandyCane, WrappedCandy {
  
  double sugarLevel();
  boolean isForbidden();
  
}
