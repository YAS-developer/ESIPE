package fr.uge.spacetravel;

public sealed interface Passenger permits Crew, Tourist {
  
	int maxHibernation();
	int age();
	String name();
	
}

