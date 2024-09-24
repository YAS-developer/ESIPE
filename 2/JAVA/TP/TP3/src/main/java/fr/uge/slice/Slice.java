package fr.uge.slice;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Slice<E> {
    int size();
    E get(int index);

    static <E> Slice<E> of(E[] elements, int from, int to) {
        if (elements == null) {
            throw new NullPointerException("elements array is null");
        }
        if (from < 0 || to > elements.length || from > to) {
            throw new IndexOutOfBoundsException("Invalid slice bounds");
        }
        return new SliceImpl<>(elements, from, to);
    }

    final class SliceImpl<E> implements Slice<E> {
        private final E[] elements;
        private final int from;
        private final int to;

        private SliceImpl(E[] elements, int from, int to) {
            this.elements = elements;
            this.from = from;
            this.to = to;
        }

        @Override
        public int size() {
            return to - from;
        }

        @Override
        public E get(int index) {
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            return elements[from + index];
        }

        @Override
        public String toString() {
            return IntStream.range(0, size())
                    .mapToObj(i -> {
                        E e = get(i);
                        return e == null ? "null" : e.toString();
                    })
                    .collect(Collectors.joining(", ", "[", "]"));
        }
    }
}