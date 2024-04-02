package fr.uge.manifest.main;

import fr.uge.manisfest.Container;
import fr.uge.manisfest.Manifest;
import fr.uge.manisfest.Passenger;

public class Main {
  public static void main(String[] args){


    // var container4 = new Container("Spain", 250);
    // var container5 = new Container("Swiss", 200);
    // var manifest2 = new Manifest();
    // manifest2.add(container4);
    // manifest2.add(container5);
    // System.out.println(manifest2);
    // 1. Spain 250kg
    // 2. Swiss 200kg

    // var passenger1 = new Passenger("France");
    // var container6 = new Container("England", 350);
    // var manifest3 = new Manifest();
    // manifest3.add(passenger1);
    // manifest3.add(container6);
    // System.out.println(manifest3);
    // // 1. France (passenger)
    // // 2. England 350kg

    // System.out.println(manifest3.price());

    var container8 = new Container("Russia", 450);
    var container9 = new Container("China", 200);
    var container10 = new Container("Russia", 125);
    var passenger2 = new Passenger("Russia");
    var manifest4 = new Manifest();
    manifest4.add(container8);
    manifest4.add(container9);
    manifest4.add(container10);
    manifest4.add(passenger2);
    System.out.println(manifest4.weight()); // 775

  }
}
