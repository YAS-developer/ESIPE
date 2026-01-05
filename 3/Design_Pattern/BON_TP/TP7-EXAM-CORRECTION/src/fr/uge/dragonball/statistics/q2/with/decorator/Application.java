package fr.uge.dragonball.statistics.q2.with.decorator;

import java.util.List;

public class Application {
  /*
  Q1: Factory
  Q2: Decorator
   */
  static void main(String[] args) {
    var goku = new BaseFighter("Goku", 9000, 10000);
    var vegeta = new BaseFighter("Vegeta", 8500, 9500);
    var gohan = new BaseFighter("Gohan", 7000, 3500);
    var trunk = new BaseFighter("Trunk", 6500, 4000);

    var fusion1 = Fighter.fusion(List.of(goku, vegeta));
    var fusion2 = Fighter.fusion(List.of(gohan, trunk));
    var megaFusion = Fighter.fusion(List.of(fusion1, fusion2));

    System.out.println("Individual Fighters:");
    System.out.println(goku);
    System.out.println(vegeta);
    System.out.println(gohan);
    System.out.println(trunk);

    System.out.println("Fusion Fighters:");
    System.out.println(fusion1);
    System.out.println(fusion2);

    System.out.println("Mega Fusion:");
    System.out.println(megaFusion);


    var superSayen = new Transformation("Super Sayen", fighter -> fighter.power() * 2, fighter -> fighter.maxHealth() / 2);
    var superSayen2 = new Transformation("Super Sayen2", fighter -> fighter.power() * 4, fighter -> fighter.maxHealth() / 4);
    var superBlue = new Transformation("Super Blue", fighter -> fighter.power() * fighter.maxHealth(), _ -> 1);


    var transform = new TransformFighter(goku, superSayen);
    var transform2 = new TransformFighter(fusion1, superBlue);
    var transform3 = new TransformFighter(megaFusion, superSayen2);

    System.out.println("With transformation:");
    System.out.println(transform);
    System.out.println(transform2);
    System.out.println(transform3);


  }

}
