package fr.uge.candy;

import java.util.Objects;

public record CandyCane(String flavor, String color) implements Treat {
  
  public CandyCane {
    Objects.requireNonNull(flavor);
    Objects.requireNonNull(color);
  }

  @Override
  public double sugarLevel() {
    if (color.equals("noir") || color.equals("orange")) {
      return 0.4;
    }
    return 0.8;
  }
  
  @Override
  public boolean isForbidden() {
    return flavor.equals("citrouille") || color.equals("orange");
  }
  
  @Override
  public String toString() {
    return "Sucre d'orge " + color +  " au parfum " + flavor;
  }
}
