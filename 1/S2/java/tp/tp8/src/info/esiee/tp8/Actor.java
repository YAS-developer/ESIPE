package info.esiee.tp8;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Actor(String firstName, String lastName) {
    public Actor {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
    }

    // Méthode pour grouper les acteurs par prénom
    public static Map<String, List<Actor>> actorGroupByFirstName(List<Actor> actors) {
        return actors.stream()
                     .collect(Collectors.groupingBy(Actor::firstName));
    }

    // Méthode générique pour grouper les acteurs par une fonction donnée
    public static Map<String, List<Actor>> actorGroupBy(List<Actor> actors, Function<Actor, String> groupByFunction) {
        return actors.stream()
                     .collect(Collectors.groupingBy(groupByFunction));
    }
}
