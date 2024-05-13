// package fr.uge.farm;

import java.util.LinkedHashMap;
import java.util.Map;

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


		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();


		map.put(1,"toto");
		map.put(2,"lulu");
		map.put(3,"YAYA");


		// System.out.println(map);


		// for(Map.Entry<Integer, String> entry: map.entrySet()){
		// 	System.out.println(entry.getKey()+": "+entry.getValue());
		// }



	}
}






/*

Les LinkedHashMap en Java sont très utiles lorsque vous avez besoin d'une structure de données de type map qui maintient l'ordre d'insertion de ses éléments. Voici quelques opérations courantes et utiles avec des LinkedHashMap :
1. Création d'un LinkedHashMap

Pour créer un LinkedHashMap, vous pouvez simplement utiliser son constructeur :

java

LinkedHashMap<Integer, String> map = new LinkedHashMap<>();

2. Ajouter ou Remplacer une Valeur

Pour ajouter une paire clé-valeur à la map ou remplacer la valeur associée à une clé spécifique :

java

map.put(1, "One");
map.put(2, "Two");

Si la clé existe déjà, put remplace la valeur précédente par la nouvelle.
3. Accès à une Valeur

Pour accéder à une valeur, utilisez la méthode get, en passant la clé correspondante :

java

String value = map.get(1); // Retourne "One"

4. Parcourir un LinkedHashMap
Parcourir par Clé-Valeur

Utilisez entrySet pour parcourir les paires clé-valeur :

java

for (Map.Entry<Integer, String> entry : map.entrySet()) {
    System.out.println("Clé: " + entry.getKey() + ", Valeur: " + entry.getValue());
}

Parcourir par Clé

Utilisez keySet si vous êtes seulement intéressé par les clés :

java

for (Integer key : map.keySet()) {
    System.out.println("Clé: " + key);
}

Parcourir par Valeur

Utilisez values si vous êtes seulement intéressé par les valeurs :

java

for (String value : map.values()) {
    System.out.println("Valeur: " + value);
}

5. Supprimer une Clé

Pour supprimer une paire clé-valeur, utilisez la méthode remove avec la clé :

java

map.remove(1); // Supprime la paire clé-valeur où la clé est 1

6. Remplacer une Valeur pour une Clé Spécifique

Pour remplacer la valeur associée à une clé spécifique (si la clé est présente) :

java

map.replace(2, "Deux"); // Remplace la valeur associée à la clé 2 par "Deux"

7. Vérifier l'Existence d'une Clé ou d'une Valeur

Pour vérifier si une clé ou une valeur est présente dans la map :

java

boolean containsKey = map.containsKey(2); // true si la clé 2 existe
boolean containsValue = map.containsValue("Deux"); // true si la valeur "Deux" existe

8. Obtenir la Taille de la Map

Pour obtenir le nombre de paires clé-valeur dans la map :

java

int size = map.size();

9. Vider la Map

Pour supprimer toutes les paires clé-valeur de la map :

java

map.clear();

 */