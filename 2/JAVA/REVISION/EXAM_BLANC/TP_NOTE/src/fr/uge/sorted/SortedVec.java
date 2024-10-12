package fr.uge.sorted;

import java.util.List;
import java.util.Objects;

public final class SortedVec<E extends Comparable<? super E>> {
    private final E[] sortedArray;

    @SuppressWarnings("unchecked")
    private SortedVec(E[] s) {
        sortedArray = s;
    }

    static void checkSortedStrings(String[] strings) {
        Objects.requireNonNull(strings);
        if (strings.length <= 1) {
            return;
        }
        for (int i = strings.length - 1; i > 0; i--) {
            if (strings[i].compareTo(strings[i-1]) < 0) {
                throw new IllegalArgumentException("Tableau non trie");
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Comparable<? super E>> SortedVec<E> ofSortedStrings(List<E> list) {
        Objects.requireNonNull(list, "La liste ne peut pas être null");
        E[] array = (E[]) new Comparable[list.size()];
        if (list.isEmpty()) {
            return new SortedVec<>(array);
        }
        int index = 0;
        E previous = null;

        for (E element : list) {
            if (element == null) {
                throw new NullPointerException("La liste contient un élément null");
            }
            if (previous != null && element.compareTo(previous) < 0) {
                throw new IllegalArgumentException("Liste non triée");
            }

            array[index] = element;
            index++;
            previous = element;
        }

        return new SortedVec<>(array);
    }

    public int size() {
        return sortedArray.length;
    }

    public E get(int index) {
        Objects.checkFromIndexSize(index, 1, sortedArray.length);
        return sortedArray[index];
    }
}