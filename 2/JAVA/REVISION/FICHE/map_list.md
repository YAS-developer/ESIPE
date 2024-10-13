fiche de révision concise sur les méthodes les plus couramment utilisées pour les Map et les List en Java :



Map
put(key, value) : Ajoute une paire clé-valeur à la map.
get(key) : Récupère la valeur associée à la clé.
remove(key) : Supprime la paire clé-valeur de la map.
containsKey(key) : Vérifie si la clé existe dans la map.
containsValue(value) : Vérifie si la valeur existe dans la map.
keySet() : Retourne un Set de toutes les clés.
values() : Retourne une Collection de toutes les valeurs.
entrySet() : Retourne un Set de toutes les paires clé-valeur.
putIfAbsent(key, value) : Ajoute la paire clé-valeur seulement si la clé n'existe pas.
getOrDefault(key, defaultValue) : Retourne la valeur pour la clé, ou une valeur par défaut si la clé n'existe pas.



Map<String, Integer> map = new HashMap<>();
map.put("A", 1);

// La clé "A" existe déjà, donc la valeur 1 est retournée sans appeler la fonction
int valueA = map.computeIfAbsent("A", k -> 100);
System.out.println("Value for A: " + valueA); // Output: Value for A: 1

// La clé "B" n'existe pas, donc la fonction est appelée et 200 est ajouté à la map
int valueB = map.computeIfAbsent("B", k -> 200);
System.out.println("Value for B: " + valueB); // Output: Value for B: 200

System.out.println(map); // Output: {A=1, B=200}
scores.merge("Bob", 50, (oldValue, newValue) -> oldValue + newValue);
List
add(element) : Ajoute un élément à la fin de la liste.
add(index, element) : Insère un élément à l'index spécifié.
get(index) : Récupère l'élément à l'index spécifié.
remove(index) : Supprime l'élément à l'index spécifié.
remove(object) : Supprime la première occurrence de l'objet spécifié.
size() : Retourne le nombre d'éléments dans la liste.
clear() : Supprime tous les éléments de la liste.
contains(object) : Vérifie si la liste contient l'objet spécifié.
indexOf(object) : Retourne l'index de la première occurrence de l'objet.
subList(fromIndex, toIndex) : Retourne une vue de la portion de la liste.





Méthodes communes aux deux
isEmpty() : Vérifie si la collection est vide.
forEach(action) : Effectue une action pour chaque élément.
Méthodes utilitaires (Collections)
Collections.unmodifiableList(list) : Crée une vue non modifiable de la liste.
Collections.sort(list) : Trie la liste.
Collections.reverse(list) : Inverse l'ordre des éléments dans la liste.
Ces méthodes couvrent la plupart des opérations courantes sur les Map et les List en Java. Maîtriser ces méthodes vous permettra de manipuler efficacement ces structures de données dans vos programmes.

