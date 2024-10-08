import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Pattern matching java: sealed interface + switch avec les types de classes permits

public class App {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);

        // Exemple de compute
        map.compute("a", (k, v) -> (v == null) ? 1 : v + 1);
        System.out.println("Après compute sur 'a': " + map); // Affiche {a=2, b=2}

        map.compute("c", (k, v) -> (v == null) ? 1 : v + 1);
        System.out.println("Après compute sur 'c': " + map); // Affiche {a=2, b=2, c=1}

        // Exemple de merge
        map.merge("b", 10, (oldValue, newValue) -> oldValue + newValue);
        System.out.println("Après merge sur 'b': " + map); // Affiche {a=2, b=12, c=1}

        map.merge("d", 5, (oldValue, newValue) -> oldValue + newValue);
        System.out.println("Après merge sur 'd': " + map); // Affiche {a=2, b=12, c=1, d=5}


         Map<String, List<String>> map2 = new HashMap<>();

        // Utilisation de computeIfAbsent pour ajouter un élément à une liste
        map2.computeIfAbsent("fruits", k -> new ArrayList<>()).add("pomme");
        map2.computeIfAbsent("fruits", k -> new ArrayList<>()).add("banane");
        map2.computeIfAbsent("légumes", k -> new ArrayList<>()).add("carotte");

        System.out.println("Contenu de la map : " + map);

        // Utilisation classique (sans computeIfAbsent) pour comparaison
        if (!map.containsKey("céréales")) {
            map.put("céréales", new ArrayList<>());
        }
        map.get("céréales").add("blé");

        System.out.println("Contenu final de la map : " + map);
    }

    public Object createToto(){
        return new Toto (){
            private final int index;
            public Toto(int index){
                this.index = index;
            }
        };
    }
    
}