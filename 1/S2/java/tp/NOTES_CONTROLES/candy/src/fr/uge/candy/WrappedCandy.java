package fr.uge.candy;

import java.util.Objects;

public record WrappedCandy(String flavor, boolean light) implements Treat {

  public WrappedCandy {
    Objects.requireNonNull(flavor);
  }

  @Override
  public String toString() {
    return "Bonbon enveloppé" + (light ? " (sans sucre)" : "") +  " au parfum " + flavor;
  }
  
  @Override
  public double sugarLevel() {
    return light ? 0.2 :0.7;
  }
  
  @Override
  public boolean isForbidden() {
    return flavor.equals("citrouille");
  }
  
}
