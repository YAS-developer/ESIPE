import java.util.stream.Stream;

public interface Jsp<T> {
    public static <E> Jsp<E> getImplementation() {
        // Classe interne locale (dans une méthode static)
        class LocalClass implements InterfaceA, InterfaceB {
            @Override
            public void doSomethingA() {
                System.out.println("Implementation of doSomethingA");
            }

            @Override
            public void doSomethingB() {
                System.out.println("Implementation of doSomethingB");
            }
        }

        // Retourner l'instance comme InterfaceA (ou tout autre interface)
        return new LocalClass();
    }

    
 
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
}