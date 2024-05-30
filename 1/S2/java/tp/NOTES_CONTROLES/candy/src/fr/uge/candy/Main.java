package fr.uge.candy;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    var ourson = new Candy("pomme", true);
    var pimousse = new Candy("fraise", false);
    var candies = new CandyBasket();
    candies.add(ourson); 
    candies.add(ourson); 
    candies.add(pimousse); 
    candies.add(ourson); 
    System.out.println("Taux de sucre moyen: " + candies.averageSugarLevel()); 
    // Taux de sucre moyen: 0.22499999999999998
    System.out.println(candies);
    // ******
    // Bonbon (sans sucre) au parfum pomme
    // Bonbon (sans sucre) au parfum pomme
    // Bonbon au parfum fraise
    // Bonbon (sans sucre) au parfum pomme
    // ******
    var orge1 = new CandyCane("cerise", "rouge");
    var orge2 = new CandyCane("réglisse", "noir");
    System.out.println(orge1 + ", taux de sucre : " + orge1.sugarLevel());
    // Sucre d'orge rouge au parfum cerise, taux de sucre : 0.8
    System.out.println(orge2 + ", taux de sucre : " + orge2.sugarLevel());
    // Sucre d'orge noir au parfum réglisse, taux de sucre : 0.4
    var ourson2 = new Candy("pomme", true);
    var pimousse2 = new Candy("fraise", false);
    var orge3 = new CandyCane("cerise", "rouge");
    var orge4 = new CandyCane("réglisse", "noir");
    var candies2 = new CandyBasket();
    candies2.add(ourson2); 
    candies2.add(pimousse2); 
    candies2.add(orge3); 
    candies2.add(orge4); 
    System.out.println("Taux de sucre moyen : " + candies2.averageSugarLevel()); 
    // Taux de sucre moyen: 0.475
    System.out.println(candies2);
    // ******
    // Bonbon (sans sucre) au parfum pomme
    // Bonbon au parfum fraise
    // Sucre d'orge rouge au parfum cerise
    // Sucre d'orge noir au parfum réglisse
    // s******
    var pimousse3 = new Candy("fraise", false);
    var orge5 = new CandyCane("cerise", "rouge");
    var pierrot = new WrappedCandy("cola", false);
    var michoco = new WrappedCandy("caramel", true);
    var candies3 = new CandyBasket();
    candies3.add(pimousse3); 
    candies3.add(orge5); 
    candies3.add(pierrot);
    candies3.add(michoco);
    System.out.println("Taux de sucre moyen: " + candies3.averageSugarLevel()); 
    // Taux de sucre moyen: 0.575
    System.out.println(candies3);
    // ******
    // Bonbon au parfum fraise
    // Sucre d'orge rouge au parfum cerise
    // Bonbon enveloppé au parfum cola
    // Bonbon enveloppé (sans sucre) au parfum caramel
    candies3.add(pierrot);
    candies3.add(michoco);
    System.out.println(candies3.countSameCandies());
    // {Bonbon enveloppé au parfum cola=2, Bonbon au parfum fraise=1, 
    // Bonbon enveloppé (sans sucre) au parfum caramel=2, Sucre d'orge rouge au parfum cerise=1}
    System.out.println(candies3.wrappedCandies());
    // [Bonbon enveloppé au parfum cola, Bonbon enveloppé (sans sucre) au parfum caramel, 
    // Bonbon enveloppé au parfum cola, Bonbon enveloppé (sans sucre) au parfum caramel]
    var pimousse4 = new Candy("fraise", false);
    var pimousse5 = new Candy("fraise", false);
    var orge6 = new CandyCane("cerise", "rouge");
    var michoco2 = new WrappedCandy("caramel", true);
    var basket1 = new CandyBasket();
    var basket2 = new CandyBasket();
    basket1.add(pimousse4); 
    basket1.add(pimousse4); 
    basket1.add(pimousse4); 
    basket1.add(orge6); 
    basket1.add(michoco2);
    basket2.add(pimousse5); // ça fait au total 4 bonbons à la fraise...
    basket2.add(orge6); 
    basket2.add(orge6); // ... 3 sucres d'orges rouges à la cerise...
    basket2.add(michoco2); // ... et 2 bonbons enveloppés (sans sucre) au caramel.
    System.out.println(CandyBasket.fairCandyBasket(List.of(basket1, basket2)));
    // ******
    // Bonbon au parfum fraise
    // Bonbon au parfum fraise
    // Bonbon enveloppé (sans sucre) au parfum caramel
    // Sucre d'orge rouge au parfum cerise
    // ******
    System.out.println(basket1);
    basket1.removeIf(treat -> treat.sugarLevel() <= 0.5);
    System.out.println(basket1);
    System.out.println(basket1.count(michoco2));
    // ******
    // Bonbon au parfum fraise
    // Bonbon au parfum fraise
    // Bonbon au parfum fraise
    // Sucre d'orge rouge au parfum cerise
    // ******
    // 0
    System.out.println(basket1.count(pimousse4));
    // 3
  }

}
