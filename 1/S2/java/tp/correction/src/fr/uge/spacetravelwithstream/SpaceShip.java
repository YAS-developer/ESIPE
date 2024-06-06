package fr.uge.spacetravelwithstream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpaceShip {
	private final String name;
	private final LinkedHashSet<Passenger> passengers;
	private final HashMap<String, HashSet<Droid>> droids;

	public SpaceShip(String name) {
		this.name = Objects.requireNonNull(name);
		passengers = new LinkedHashSet<>();
		droids = new HashMap<>();
	}

	public void addPassenger(Passenger passenger) {
		Objects.requireNonNull(passenger);
		if (! passengers.add(passenger)) {
		  throw new IllegalStateException();
		}
	}

  public int minMaxHibernation() {
    return passengers.stream()
                     .mapToInt(Passenger::maxHibernation)
                     .min()
                     .orElseThrow();
  }
  
  // avec le pattern matching de Java 19
  private static boolean isTourist(Passenger passenger) {
    Objects.requireNonNull(passenger);
    return switch(passenger) { 
      case Crew p -> false;
      case Tourist p -> true;
    }; 
  }
  
  private static boolean isCrew(Passenger passenger) {
    Objects.requireNonNull(passenger);
    return ! isTourist(passenger);
  }
  
  public double meanTouristAge() {
    return passengers.stream()
                     .filter(SpaceShip::isTourist)
                     .mapToDouble(Passenger::age)
                     .average()
                     .orElseThrow();
  }
  
  public Map<Integer, List<Passenger>> passengersByAge(){
    var map = passengers.stream()
                        .collect(Collectors.groupingBy(Passenger::age));
    return Map.copyOf(map);
  }
  
  // avec le pattern matching de Java 19
  public Map<Integer, List<Tourist>> touristsByAge(){
    var map = passengers.stream()
                        .flatMap(p -> switch (p) {
                           case Crew crew -> null;
                           case Tourist tourist -> Stream.of(tourist);
                         })
                      .collect(Collectors.groupingBy(Tourist::age));
    return Map.copyOf(map);
  }

  public void removeCrew() {
    passengers.removeIf(SpaceShip::isCrew);
  }
  

  public void addDroid(Droid droid) {
    Objects.requireNonNull(droid);
    droids.computeIfAbsent(droid.role(), key -> new HashSet<>()).add(droid);
  }
  
  public boolean searchDroid(Droid droid) {
    Objects.requireNonNull(droid);
    return droids.getOrDefault(droid.role(), new HashSet<Droid>()).contains(droid);
  }
  
  public void reprogramDroids(String role, String newRole) {
	  Objects.requireNonNull(role);
	  Objects.requireNonNull(newRole);
	  if (role.equals(newRole)) {
      return ;
    }
		var set = droids.remove(role);
		if (set == null) {
			return ;
		}
    var reprogramSet = set.stream()
                          .map(d -> d.reprogram(newRole))
                          .collect(Collectors.toSet());
		droids.computeIfAbsent(newRole,  key -> new HashSet<Droid>()).addAll(reprogramSet);
	}
	
	@Override
	public String toString() {
    var line = "----------------------\n";
	  var s = name + "\n" + line;
	  return s + passengers.stream()
	      .map(Passenger::toString)
	      .collect(Collectors.joining("\n"))
	           + "\n" 
	           + droids.entrySet().stream()
	      .map(e -> e.getKey() + " - " + e.getValue())
	      .collect(Collectors.joining("\n"));
	}

}

