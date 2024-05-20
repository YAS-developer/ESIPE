package fr.umlv.info.main;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MainExo4 {

    private static <T> long count(List<T> list, T search) {
        var res = 0;
        for (T elt : list) {
            if (elt.equals(search))
                res++;
        }
        return res;
    }

    /**
     * pour obtenir un stream à partir d'un objet de type List, il suffit d'appeller la méthode stream() dessus.
     * <p>
     * La méthode pour filter un stream est la métode filter();
     * La méthde pour counter le nombre d'élément est count();
     * <p>
     * Dans notre cas, T correspond au type String.
     * <p>
     * "elt->elt.equals(search)"
     * C'est la lambda qui filtre l'élément qui est égal à notre mot recherché
     */
    private static <T> long count2(List<T> list, T search) {
        return list.stream().filter(elt -> elt.equals(search)).count();
    }

    /*Nous allons faire un mapToLong car nous cherchons à renvoyer un entier sur 32bits*/
    private static <T> long count3(List<T> list, T search) {
        return list.stream().filter(elt -> elt.equals(search)).mapToLong(elt -> 1).reduce(0, Long::sum);
    }

    private static void printAndTime(Supplier<Long> function) {
        var start = System.nanoTime();

        // Calcul
        var result = function.get();

        var end = System.nanoTime();
        System.out.println("result " + result);
        System.out.println(" elapsed time " + (end - start));
    }


    /**
     * La variable locale list2 contient la même liste à chaque execution de 1_000_000 entier entre 0 et 100
     * converti en String, le tout mis dans une liste
     */
    public static void main(String[] args) {
        var list2 =
                new Random(0)
                        .ints(1_000_000, 0, 100)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.toList());

        printAndTime(() -> count(list2, "33"));
        printAndTime(() -> count2(list2, "33"));
        printAndTime(() -> count3(list2, "33"));
    }
}
