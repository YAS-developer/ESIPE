package fr.uge.dragonball.statistics.q1;

import java.util.List;

public class Application {

  /*
  Q1: Factory
   */
  static void main(String[] args) {
    var goku = new Fighter("Goku", 9000, 10000);
    var vegeta = new Fighter("Vegeta", 8500, 9500);
    var gohan = new Fighter("Gohan", 7000, 3500);
    var trunk = new Fighter("Trunk", 6500, 4000);

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



  }

}
