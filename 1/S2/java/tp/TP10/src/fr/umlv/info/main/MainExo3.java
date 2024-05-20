package fr.umlv.info.main;

import java.util.List;

public class MainExo3 {


    /*Nous allons faire un mapToLong car nous cherchons à renvoyer un entier sur 32bits*/
    private static <T> long count3(List<T> list, T search) {
        return list.stream().filter(elt -> elt.equals(search)).mapToLong(elt -> 1L).reduce(Long::sum).getAsLong();
    }

    public static void main(String[] args) {
        var list = List.of("hello", "world", "hello", "lambda");
        System.out.println(count3(list, "hello"));  // 2


    }

}
