package fr.uge.spacetravelwithstream;

public sealed interface Passenger permits Crew, Tourist {
  
	int maxHibernation();
	int age();
	
}

