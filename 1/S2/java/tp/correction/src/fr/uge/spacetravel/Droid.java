package fr.uge.spacetravel;

import java.util.Objects;

public record Droid(int id, String role) {
	
	public Droid {
	  Objects.requireNonNull(role);
	}

	public Droid reprogram(String role) {
	  Objects.requireNonNull(role);
	  return new Droid(id, role);
	}

	@Override
	public String toString() {
		return "Droid " + id + " (" + role + ")";
	}

}
