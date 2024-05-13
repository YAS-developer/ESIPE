package info.esiee.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import info.esiee.tp8.Actor;
import info.esiee.tp8.Lambdas;


public class Main {
	public static void main(String[] args) {
    // Example usage of the upperCaseAll method
    List<String> words = List.of("hello", "world", "java", "programming");
//    System.out.println("Before: " + words);
    
    // Make a mutable copy of the list since List.of returns an immutable list
    List<String> mutableWords = new ArrayList<>(words);
    Lambdas.upperCaseAll(mutableWords);
   
//    System.out.println("After: " + mutableWords)

    
//    List<String> list = List.of("foo", "bar", "foo");
//    Map<String, Integer> result = Lambdas.occurrences(list);
//    System.out.println(result);  
    
    var actors = new ArrayList<Actor>();
    
    actors.add(new Actor("Toto", "ZAEAE"));
    actors.add(new Actor("Toto", "MAMAEEEOOO"));
    actors.add(new Actor("Kiki", "Loaze"));
    actors.add(new Actor("Baril", "gneugneu"));
  
    
    System.out.println(Actor.actorGroupByFirstName(actors));
	}
}
