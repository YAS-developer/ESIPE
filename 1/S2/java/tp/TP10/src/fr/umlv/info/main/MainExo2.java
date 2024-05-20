package fr.umlv.info.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainExo2 {

    private static List<String> upperCase(List<String> list) {
        List<String> res = new ArrayList<>();

        for (var elt : list) {
            res.add(elt.toUpperCase());
        }

        return res;
    }

    /**
     * On peut utiliser Stream.map pour appliquer une fonction à tous les éléments du stream
     * ça renvoie un nouveau stream de tous les éléments qui ont subi la modification.
     */
    private static List<String> upperCase2(List<String> list) {
        List<String> res = new ArrayList<>();
        list.stream().map(elt -> elt.toUpperCase()).forEach(elt -> res.add(elt));
        return res;
    }

    private static List<String> upperCase3(List<String> list) {
        List<String> res = new ArrayList<>();
        list.stream().map(String::toUpperCase).forEach(res::add);
        return res;
    }

    private static List<String> upperCase4(List<String> list) {
        return list.stream().map(String::toUpperCase).collect(Collectors.toList());
    }


    public static void main(String[] args) {
        var list = List.of("hello", "world", "hello", "lambda");
        System.out.println(upperCase(list));  // [HELLO, WORLD, HELLO, LAMBDA]
        System.out.println(upperCase2(list));  // [HELLO, WORLD, HELLO, LAMBDA]
        System.out.println(upperCase3(list));  // [HELLO, WORLD, HELLO, LAMBDA]
        System.out.println(upperCase4(list));  // [HELLO, WORLD, HELLO, LAMBDA]
    }
}
