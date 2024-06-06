package fr.uge.spacetravelwithstream;


public class Main {
	public static void main(String[] args) {
		var droid = new Droid(42,"chemist");
		System.out.println(droid); 
		droid.reprogram("engineer");
		System.out.println(droid); 
		var crew = new Crew("James", 40, Rank.ROOKIE);
    System.out.println(crew); 
    var tourist = new Tourist("Jeff", 50, true);
    System.out.println(tourist); 
    var tourist2 = new Tourist("Elon", 25, false);
    System.out.println(tourist2); 
    var tourist3 = new Tourist("John", 50, true);
    System.out.println(tourist3); 
		// --------------------------
		var rocinante = new SpaceShip("Rocinante");
		rocinante.addPassenger(new Tourist("Jeff", 50, true));
		rocinante.addPassenger(new Tourist("Elon", 25, false));
		rocinante.addPassenger(new Tourist("John", 50, true));
		rocinante.addPassenger(new Crew("James", 40, Rank.ROOKIE));
		rocinante.addPassenger(new Crew("Naomi", 50, Rank.STAFF));
		rocinante.addPassenger(new Crew("Amos", 45, Rank.OFFICER));
		System.out.println(rocinante);
		System.out.println(rocinante.minMaxHibernation());
		System.out.println(rocinante.meanTouristAge());
	  System.out.println(rocinante.passengersByAge());
	  System.out.println(rocinante.touristsByAge());
	  rocinante.removeCrew();
	  System.out.println(rocinante);
		// --------------------------
		var razorbak = new SpaceShip("Razorback");
		razorbak.addDroid(new Droid(3451435, "fire-gunner"));
		razorbak.addDroid(new Droid(42, "fire-gunner"));
		razorbak.addDroid(new Droid(44, "astronaut"));
		razorbak.addDroid(new Droid(4111, "astronaut"));
		razorbak.addDroid(new Droid(40, "icebreaker"));
		razorbak.addPassenger(new Crew("Camina", 30, Rank.OFFICER));
		razorbak.addPassenger(new Crew("James", 40, Rank.ROOKIE));
		System.out.println(razorbak);
		System.out.println(razorbak.searchDroid(new Droid(42, "fire-gunner"))); // true
		// ----------------------
		razorbak.reprogramDroids("astronaut", "astrologist"); // 2
		razorbak.reprogramDroids("fire-gunner", "icebreaker"); // 2
		System.out.println(razorbak);
		System.out.println(razorbak.searchDroid(new Droid(42, "icebreaker"))); // true
	// ----------------------
	}
}
