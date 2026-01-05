package fr.uge.numeric;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class NumericVec<E> extends AbstractList<E> implements RandomAccess {
    private long[] longs;
    private int size;
    private final ToLongFunction<E> into;
    private final LongFunction<E> from;

    private NumericVec(long[] longs, ToLongFunction<E> into, LongFunction<E> from){
        this.longs = longs;
        this.size = longs.length;
        this.into = Objects.requireNonNull(into);
        this.from = Objects.requireNonNull(from);
        super();
    }
    public static NumericVec<Long> longs(long... longs){
        return new NumericVec<>(Arrays.copyOf(longs, longs.length), Long::longValue, Long::valueOf);
    }
    @Override
    public E get(int index){
        Objects.checkIndex(index, size);
        return from.apply(longs[index]);
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public boolean add(E element){
        Objects.requireNonNull(element);
        var newElement = into.applyAsLong(element);
        if(size == longs.length){
            var newLength = size == 0 ? 1 : size << 1;
            longs = Arrays.copyOf(longs, newLength);
        }
        longs[size++] = newElement;
        return true;
    }
    public static NumericVec<Integer> ints(int... ints){
        Objects.requireNonNull(ints);
        var longs = Arrays.stream(ints).mapToLong(Long::valueOf).toArray();
        return new NumericVec<>(longs, Integer::longValue, l -> Long.valueOf(l).intValue());
    }
    public static NumericVec<Double> doubles(double... doubles){
        Objects.requireNonNull(doubles);
        var longs = Arrays.stream(doubles).mapToLong(Double::doubleToLongBits).toArray();
        return new NumericVec<>(longs, Double::doubleToRawLongBits, Double::longBitsToDouble);
    }
    private Spliterator<E> fromArray(int start, int end, long[] longs){
        return new Spliterator<>() {
            private int i = start;
            @Override
            public boolean tryAdvance(Consumer<? super E> action) {
                if(i < end){
                    action.accept(from.apply(longs[i++]));
                    return true;
                }
                return false;
            }
            @Override
            public Spliterator<E> trySplit() {
                if(size < 1024){
                    return null;
                }
                var middle = (i + end) >>> 1;
                if(middle == i){
                    return null;
                }
                var spliterator = fromArray(i, middle, longs);
                i = middle;
                return spliterator;
            }
            @Override
            public long estimateSize() {
                return end - i;
            }
            @Override
            public int characteristics() {
                return NONNULL | ORDERED | IMMUTABLE | SIZED;
            }
        };
    }
    public Stream<E> stream(){
        return StreamSupport.stream(spliterator(), false);
    }
    @Override
    public Spliterator<E> spliterator() {
        return fromArray(0, size, longs);
    }
}