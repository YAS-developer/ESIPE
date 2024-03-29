package fr.uge.farm;

public interface Farmable {
	String name();
	int date();
	default boolean isCow() {return false;}
}
