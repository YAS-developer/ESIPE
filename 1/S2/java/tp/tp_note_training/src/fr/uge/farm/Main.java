package fr.uge.farm;

public class Main {
	public static void main(String[] args) {
//		System.out.println("Hello");
		var daisy = new Cow("daisy", 2);
//		System.out.println(daisy.name()); // daisy
//		System.out.println(daisy); // daisy (Cow)
		var ina = new Cow("ina", 12);
//		System.out.println(daisy.isOlder(ina)); // true
//		System.out.println(ina.isOlder(ina)); // true
		
		var farm = new Farm(0);
//		System.out.println(farm.date()); // 0
		farm.add(daisy);
		farm.add(ina);
//		System.out.println(farm.residents()); // [daisy (Cow), ina (Cow)]
		var gimly = new Farmer("gimly", 10);
		farm.add(gimly);
//		System.out.println(farm.residents());
		
//		System.out.println(farm);
//		System.out.println(farm.stat());
		farm.add(new Cow("bella", 2));
		farm.add(new Cow("marguerite", 12));
		farm.add(new Farmer("tho", 12));
		farm.add(new Farmer("dwa", 14));
		System.out.println(farm.residentsByDate());
	}
}
