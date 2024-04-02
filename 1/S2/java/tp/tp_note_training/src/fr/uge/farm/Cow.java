// package fr.uge.farm;

import java.util.Objects;

public record Cow(String name, int date) implements Farmable{
	public Cow{
		Objects.requireNonNull(name, "Name must be not null.");
		if(date < 0) {
			throw new IllegalArgumentException("la date doit être supérieur ou égale à 0.");
		}
	}
	
	
	public boolean isOlder(Cow c){
		return this.date <= c.date(); 
	}
	
	@Override
	public boolean isCow(){
		return true;
	}
	
	@Override 
	public String toString() {
		return this.name()+" (Cow)";
	}
}
