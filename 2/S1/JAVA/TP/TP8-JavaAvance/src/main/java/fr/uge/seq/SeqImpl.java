package fr.uge.seq;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

final class SeqImpl<T, R> implements Seq<R> {
    private final List<T> list;
    private final Function<? super T, ? extends R> mapper;

    SeqImpl(List<T> list, Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(mapper);
        this.list = list;
        this.mapper = mapper;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public R get(int index) {
        Objects.checkIndex(index, list.size());
        return mapper.apply(list.get(index));
    }

    @Override
    public <V> Seq<V> map(Function<? super R, ? extends V> newMapper) {
        Objects.requireNonNull(newMapper);
        return new SeqImpl<>(list, mapper.andThen(newMapper));
    }

    @Override
    public Optional<R> findFirst() {
        if (list.isEmpty()){
            return Optional.empty();
        } else
            return Optional.of(mapper.apply(list.getFirst()));
    }

    @Override
    public Stream<R> stream() {
        return StreamSupport.stream(
                new Spliterator<>() {
                    private int current = 0;

                    @Override
                    public boolean tryAdvance(Consumer<? super R> action) {
                        Objects.requireNonNull(action);
                        if (current < list.size()) {
                            action.accept(mapper.apply(list.get(current++)));
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public Spliterator<R> trySplit() {
                        int remaining = list.size() - current;
                        if (remaining < 2) {
                            return null;
                        }
                        int mid = current + remaining / 2;
                        List<T> subList = list.subList(current, mid);
                        current = mid;

                        return new Spliterator<>() {
                            private int localIndex = 0;

                            @Override
                            public boolean tryAdvance(Consumer<? super R> action) {
                                if (localIndex < subList.size()) {
                                    action.accept(mapper.apply(subList.get(localIndex++)));
                                    return true;
                                }
                                return false;
                            }

                            @Override
                            public Spliterator<R> trySplit() {
                                return null;
                            }

                            @Override
                            public long estimateSize() {
                                return subList.size() - localIndex;
                            }

                            @Override
                            public int characteristics() {
                                return Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
                            }
                        };
                    }

                    @Override
                    public long estimateSize() {
                        return list.size() - current;
                    }

                    @Override
                    public int characteristics() {
                        return Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
                    }
                }, false
        );
    }

    @Override
    public Iterator<R> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public R next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return mapper.apply(list.get(index++));
            }
        };
    }

    @Override
    public String toString() {
        return list.stream()
                .map(mapper)
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "<", ">"));
    }
}
