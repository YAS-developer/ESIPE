package fr.uge.candy;

import java.util.Objects;

public record Candy(String flavor, boolean light) implements Treat {
  
  public Candy {
    Objects.requireNonNull(flavor);
  }

  @Override
  public String toString() {
    return "Bonbon" + (light ? " (sans sucre)" : "") +  " au parfum " + flavor;
  }
  
  @Override
  public double sugarLevel() {
    if (light) {
      return 0.1;
    } 
    return 0.6;
  }
  
  @Override
  public boolean isForbidden() {
    return flavor.equals("citrouille");
  }
  
}
