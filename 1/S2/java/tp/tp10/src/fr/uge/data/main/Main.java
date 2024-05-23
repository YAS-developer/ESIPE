package fr.uge.data.main;
//import fr.uge.data.*;

import fr.uge.data.LinkedLink;

public class Main {
  public static void main(String[] args) {
    // var list = new LinkedLink<String>();
    // list.add("hello");
    // list.add("world");

    // list.forEach(s -> System.out.println("string " + s + " length " + s.length()));

    var l = new LinkedLink<Integer>();
    l.add(24);
    l.add(17);
    l.add(12);
    l.removeIf(i -> i % 2 == 0);
    System.out.println(l); // 17
 }
}
