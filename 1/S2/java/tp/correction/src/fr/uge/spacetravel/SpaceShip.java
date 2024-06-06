package fr.uge.spacetravel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    if (passengers.isEmpty()) {
      throw new IllegalStateException();
    }
    int min = passengers.getFirst().maxHibernation();
    for (var passenger: passengers) {
        min = Math.min(min, passenger.maxHibernation());
    }
    return min;
  }
  
  // avec le pattern matching de Java 19
  private static boolean isTourist(Passenger passenger) {
    return switch(passenger) { 
      case Crew p -> false;
      case Tourist p -> true;
    }; 
  }
  
  // avec le pattern matching de Java 19
  public double meanTouristAge() {
    var sum = 0.0;
    var number = 0;
    for (var passenger: passengers) {
      if (isTourist(passenger)) {
        sum = sum + passenger.age();
        number = number + 1;
      }
    }
    if (number == 0) {
      return 0;
    }
    return sum/number;
  }
  
 
  public Map<Integer, List<Passenger>> passengersByAge(){
    var map = new HashMap<Integer, List<Passenger>>();
    for (var passenger: passengers) {
      var list = map.getOrDefault(passenger.age(), new ArrayList<Passenger>());
      list.add(passenger);
      map.put(passenger.age(), list);
   // map.computeIfAbsent(passenger.age(), key -> new ArrayList<>()).add(passenger);
    }
    return Map.copyOf(map);   
  }
  
  // avec le pattern matching de Java 19
  public Map<Integer, List<Tourist>> touristsByAge(){
    var map = new HashMap<Integer, List<Tourist>>();
    for (var passenger: passengers) {
      switch(passenger) {
        case Crew p -> {}
        case Tourist p -> {
          var list = map.getOrDefault(passenger.age(), new ArrayList<Tourist>());
          list.add(p);
          map.put(passenger.age(), list);
         // map.computeIfAbsent(passenger.age(), key -> new ArrayList<>()).add(passenger);
        }
      }
    }
    return Map.copyOf(map);
  }

  // avec le pattern matching de Java 19
  public void removeCrew() {
    var iterator = passengers.iterator();
    while(iterator.hasNext()) {
      var passenger = iterator.next(); 
      if (! isTourist(passenger)) {
        iterator.remove();
      }
    }
  }
  
 public void removeByName(String name) {
   Objects.requireNonNull(name);
   var iterator = passengers.iterator();
   while(iterator.hasNext()) {
     var passenger = iterator.next(); 
     if (passenger.name().equals(name)) {
       iterator.remove();
     }
   }
 }

  public void addDroid(Droid droid) {
    Objects.requireNonNull(droid);
    var set = droids.getOrDefault(droid.role(), new HashSet<Droid>());
    set.add(droid);
    droids.put(droid.role(), set);
//  droids.computeIfAbsent(droid.role(), key -> new HashSet<>())
//  .add(droid);
    
  }
  
  public boolean searchDroid(Droid droid) {
    Objects.requireNonNull(droid);
    return droids.getOrDefault(droid.role(), new HashSet<Droid>())
        .contains(droid);
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
    var reprogramSet = new HashSet<Droid>();
		for (var droid : set) {
	    reprogramSet.add(droid.reprogram(newRole));
		}
	  // set.forEach(d -> reprogramSet.add(d.reprogram(newRole));
		var newSet = droids.getOrDefault(newRole, new HashSet<Droid>());
		newSet.addAll(reprogramSet);
		droids.put(newRole, newSet);
		// droids.computeIfAbsent(newRole,  key -> new HashSet<Droid>()).addAll(reprogramSet);
	}
	
  @Override
  public String toString() {
    var line = "----------------------";
    var builder = new StringBuilder(name);
    builder.append("\n").append(line);
    for (Passenger passenger : passengers) {
      builder.append("\n").append(passenger);
    }
    for (var entry : droids.entrySet()) {
      builder.append("\n").append(entry.getKey()).append(" - ").append(entry.getValue());
    }
    return builder.toString();
  }

//	
//	@Override
//	public String toString() {
//    var line = "----------------------\n";
//	  var s = name + "\n" + line;
//	  return s + passengers.stream()
//	      .map(Passenger::toString)
//	      .collect(Collectors.joining("\n"))
//	           + "\n" 
//	           + droids.entrySet().stream()
//	      .map(e -> e.getKey() + " - " + e.getValue())
//	      .collect(Collectors.joining("\n"));
//	}

}

