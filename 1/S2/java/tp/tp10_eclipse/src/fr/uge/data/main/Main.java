package fr.uge.data.main;

import fr.uge.data.LinkedLink;

public class Main {
	 public static void main(String[] args) {
     LinkedLink<String> list = new LinkedLink<>();
     list.add("hello");
     list.add("world");

     list.forEach(s -> System.out.println("string " + s + " length " + s.length()));
 }
}
