package fr.uge.slice;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Slice<E> {
    int size();
    E get(int index);
    Slice<E> subSlice(int from, int to);
    void replaceAll(UnaryOperator<E> operator);

    default Slice<E> reversed() {
        return new Slice<E>() {
            @Override
            public int size() {
                return Slice.this.size();
            }

            @Override
            public E get(int index) {
                return Slice.this.get(size() - 1 - index);
            }

            @Override
            public Slice<E> subSlice(int from, int to) {
                Objects.checkFromToIndex(from, to, size());
                return Slice.this.subSlice(size() - to, size() - from).reversed();
            }

            @Override
            public Slice<E> reversed() {
                return Slice.this;
            }

            @Override
            public void replaceAll(UnaryOperator<E> operator) {
                Objects.requireNonNull(operator);
                Slice.this.replaceAll(operator);
            }
            @Override
            public String toString() {
                return Arrays.stream(Slice.this.elements, Slice.this.from, Slice.this.to)
                        .map(e -> e == null ? "null" : e.toString())
                        .toList()
                        .reversed()
                        .stream()
                        .collect(Collectors.joining(", ", "[", "]"));
            }
        };
    }
    
    static <E> Slice<E> of(E[] elements, int from, int to) {
        Objects.requireNonNull(elements, "elements array is null");
        Objects.checkFromToIndex(from, to, elements.length);
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
            Objects.checkIndex(index, size());
            return elements[from + index];
        }

        @Override
        public Slice<E> subSlice(int fromIndex, int toIndex) {
            Objects.checkFromToIndex(fromIndex, toIndex, size());
            return new SliceImpl<>(elements, from + fromIndex, from + toIndex);
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
            for (int i = from; i < to; i++) {
                elements[i] = operator.apply(elements[i]);
            }
        }

        @Override
        public String toString() {
            return Arrays.stream(elements, from, to).toList().toString();
        }
    }
}