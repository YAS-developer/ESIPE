package fr.uge.spacetravelwithstream;

import java.util.Objects;

public record Crew(String name, int age, Rank rank) implements Passenger {

	private static final int AGE_MAX = 60;

	public Crew {
		Objects.requireNonNull(name);
    Objects.requireNonNull(rank);
	  if (age < 0 || age > AGE_MAX) {
      throw new IllegalArgumentException();
    }
	}
	
	@Override
	public int maxHibernation() {
		return switch (rank) {
		  case ROOKIE -> 10;
		  case STAFF -> 15;
		  case OFFICER -> 30;
		};
	}
	
	@Override
  public String toString() {
    return "Crew " + name + " (" + age + " years " + rank + ")";
  }

}
