package fr.esiee.stream;

import java.util.List;
import java.util.Objects;

public class Streams {
  public record Person(String name, int age) {}
  
  public static List<String> namesOfTheAdults(List<Person> persons) {

    Objects.requireNonNull(persons, "La liste de personnes ne peut pas être vide");
    
    return persons.stream()
        .filter(person -> person.age > 18)
        .map(person -> person.name)
//        .map(Person::name)
        .toList(); 
  }

  public static void main(String[] args) {
    var persons = List.of(
        new Person("Ana", 21),
        new Person("John", 17),
        new Person("Liv", 29));
    var names = namesOfTheAdults(persons);
    System.out.println(names);
  }

}
