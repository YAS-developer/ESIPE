package fr.umlv.info.main;

import java.util.List;

public class MainExo1 {
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

    public static void main(String[] args) {
        var list = List.of("hello", "world", "hello", "lambda");
        System.out.println(count(list, "hello"));  // 2


    }
}
