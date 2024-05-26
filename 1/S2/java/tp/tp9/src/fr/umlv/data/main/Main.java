package fr.umlv.data.main;

import fr.umlv.data.LinkedLink;


public class Main {
    public static void main(String[] args) {
        /*
        var lst = new LinkedLink();

        lst.add("salut");
        lst.add("C'est moi");

        System.out.println(lst);

        System.out.println(((String) lst.get(1)).length());
        */

        /* Question 4:
         *
         * Ici on n'aime pas les cast, en effet, dans ce main, on sait que ce
         * sont des string, mais si jamais je mettais n'importe quel autre
         * type qui ne peut pas être cast en String, tout explose.
         * Comme c'est quelque chose qu'on ne peut véifier que à l'éxécution,
         * ce n'est pas une bonne pratique
         */

        var lst = new LinkedLink<String>();



        lst.add("salut");
        lst.add("C'est moi");

        System.out.println(lst);

        System.out.println(lst.get(1).length());
    }
}
