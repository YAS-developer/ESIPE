package fr.uge.seq;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public sealed interface Seq<T> extends Iterable<T> permits SeqImpl {
    int size();
    T get(int index);

    // Applique une transformation paresseuse aux éléments
    <R> Seq<R> map(Function<? super T, ? extends R> mapper);

    // Renvoie le premier élément ou un Optional.empty() s'il n'existe pas
    Optional<T> findFirst();

    // Renvoie un flux des éléments
    Stream<T> stream();

    // Factory method: Seq à partir d'une liste
    static <T> Seq<T> from(List<? extends T> list) {
        Objects.requireNonNull(list);
        return new SeqImpl<>(List.copyOf(list), Function.identity());
    }

    // Factory method: Seq à partir d'éléments individuels
    @SafeVarargs
    static <T> Seq<T> of(T... elements) {
        Objects.requireNonNull(elements);
        return from(List.of(elements));
    }
}

// Seq<U> : Le résultat de map après la trannsformation d'un T en est une nouvelle séquence contenant des éléments de type U, ici par exemple peluche, après transformation des éléments d'origine.
//? super T : Permet à la fonction de transformation de prendre en entrée girafe ou tout type parent (comme animal). Cela rend la fonction plus flexible en acceptant différents types d'entrées.
//? extends U : Permet à la fonction de transformation de retourner non seulement U (par exemple, peluche), mais aussi tout type dérivé (comme pelucheLuxe), ce qui offre plus de flexibilité pour le type de retour.
