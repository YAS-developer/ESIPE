package fr.uge.candy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CandyBasket {
  
  private final ArrayList<Treat> basket;
  private final HashMap<Treat, Integer> occurrence;
  
  public CandyBasket() {
    basket = new ArrayList<>();
    occurrence = new HashMap<>(); 
  }

  // traitement des sucreries interdites avec un switch
  private static boolean isForbidden2(Treat treat) {
    Objects.requireNonNull(treat);
    return switch(treat) {
      case CandyCane(String flavor, String color) 
         when flavor.equals("citrouille") || color.equals("orange") -> true;
      case Candy(String flavor, boolean light) 
         when flavor.equals("citrouille") -> true;
      case WrappedCandy(String flavor, boolean light) 
         when flavor.equals("citrouille") -> true;
      default -> false;
    };
  }
     
  
  public void add(Treat treat) {
    Objects.requireNonNull(treat);
    if (treat.isForbidden()) {
      throw new IllegalArgumentException();
    }
    basket.add(treat);
    occurrence.merge(treat, 1, Math::addExact);
  }
  
  private void addMulti(Treat treat, int number) {
    Objects.requireNonNull(treat);
    if (treat.isForbidden() || number < 0) {
      throw new IllegalArgumentException();
    }
    for (int i= 0; i < number; i++) {
      basket.add(treat);
    }
  }
  
  public int count(Treat treat) {
    Objects.requireNonNull(treat);
    return occurrence.getOrDefault(treat, 0);
  }
  
  public void removeIf(Predicate<? super Treat> predicate) {
    Objects.requireNonNull(predicate);
    var iterator = basket.iterator();
    while(iterator.hasNext()) {
      var treat = iterator.next(); 
      if (predicate.test(treat)) {
        iterator.remove();
        occurrence.compute(treat, (k, v) -> v - 1);
        occurrence.remove(treat, 0);
      }
    }
  }
  
  public double averageSugarLevel() {
    return  basket.stream()
                  .mapToDouble(Treat::sugarLevel)
                  .average()
                  .orElse(0);
  }
 
  public Map<Treat, Long> countSameCandies() {
    return basket.stream()
                 .collect(Collectors.groupingBy(t -> t, Collectors.counting()));
  }
  
  private static boolean isWrappedCandy(Treat treat) {
    Objects.requireNonNull(treat);
    return switch(treat) {
      case WrappedCandy w -> true;
      default -> false;
    };
  }
  
  public List<Treat> wrappedCandies() {
    return basket.stream()
                 .filter(CandyBasket::isWrappedCandy)
                 .toList();
  }
  
  public static CandyBasket fairCandyBasket(List<CandyBasket> list) {
    Objects.requireNonNull(list);
    if (list.isEmpty()) {
      return new CandyBasket();
    }
    var map = list.stream()
                  .flatMap(b -> b.basket.stream())
                  .collect(Collectors.groupingBy(t -> t, Collectors.counting()));
    var basket = new CandyBasket();
    map.forEach((k,v) -> basket.addMulti(k, (int) (v.longValue()/list.size())));
    return basket;
  }
  
  
  @Override
  public String toString() {
    return basket.stream()
                 .map(Treat::toString)
                 .collect(Collectors.joining("\n","******\n","\n******")) ;
  }
  
}
