// package fr.uge.farm;

public record Stat(int cows, int farmers) {
	
	public Stat() {
		this(0,0);
	}
	
	public Stat add(Stat s) {
		return new Stat(this.cows+s.cows(), this.farmers + s.farmers());
	}
}
