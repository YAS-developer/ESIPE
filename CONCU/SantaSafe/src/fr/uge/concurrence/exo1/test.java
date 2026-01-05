package fr.uge.concurrence.exo1;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class test {
	public static void main(String[] args) {
		
		
		var hashmap = new HashMap<String, Integer>();
		
//		var value = hashmap.computeIfAbsent("ratio", _ -> 1);
		
		var value2 = hashmap.computeIfAbsent("ratio", _ -> 5);
		
		var toto = hashmap.merge("ratio", 10, (oldValue, newValue) -> oldValue+ newValue*2);
		
		
		
		System.out.println(toto);
		
		
		/*
		 * IntStream.range(0, 10).forEach(_ ->{ System.out.println("Toto"); });;
		 * 
		 * 
		 * List<Integer> numbers = List.of(1, 2, 3, 4, 5);
		 * 
		 * var moyenne = numbers.stream() .mapToInt(Integer::intValue) .average()
		 * .orElse(0);
		 * 
		 * var somme = numbers.stream() .mapToInt(Integer::intValue) .sum();
		 */
	
	
	
	}
}
