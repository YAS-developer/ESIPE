// package fr.uge.farm;

import java.util.Objects;

public record Farmer(String name, int date) implements Farmable{
	public Farmer{
		Objects.requireNonNull(name, "Name must be not null.");
		if(date < 0) {
			throw new IllegalArgumentException("la date doit être supérieur ou égale à 0.");
		}
	}
	

	@Override 
	public String toString() {
		return this.name()+" (Farmer)";
	}
}
