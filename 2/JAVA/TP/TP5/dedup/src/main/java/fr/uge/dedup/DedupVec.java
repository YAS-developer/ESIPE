package fr.uge.dedup;

import java.util.*;

public final class DedupVec<T> extends AbstractList<T> implements RandomAccess {
    private final HashMap<T, T> map;
    private final ArrayList<T> list;

    public DedupVec() {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();
    }

    private DedupVec(Collection<? extends T> c) {
        this.map = new HashMap<>(c.size());
        this.list = new ArrayList<>(c.size());
        for (T element : c) {
            add(element);
        }
    }

    @Override
    public boolean add(T element) {
        Objects.requireNonNull(element);
        T existingElement = map.putIfAbsent(element, element);
        list.add(existingElement == null ? element : existingElement);
        return true;
    }

    @Override
    public void add(int index, T element) {
        Objects.requireNonNull(element);
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        T existingElement = map.putIfAbsent(element, element);
        list.add(index, existingElement == null ? element : existingElement);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object o) {
        return o != null && map.containsKey(o);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T element : c) {
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(list.subList(fromIndex, toIndex));
    }

    public void addFirst(T element) {
        add(0, element);
    }

    public void addLast(T element) {
        add(size(), element);
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(0);
    }

    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(size() - 1);
    }

    @Override
    public List<T> reversed() {
        return new AbstractList<T>() {
            @Override
            public T get(int index) {
                return DedupVec.this.get(size() - 1 - index);
            }

            @Override
            public int size() {
                return DedupVec.this.size();
            }

            @Override
            public void add(int index, T element) {
                DedupVec.this.add(size() - index, element);
            }
        };
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public static <E> DedupVec<E> fromSet(Set<? extends E> set) {
        Objects.requireNonNull(set, "Set cannot be null");
        return new DedupVec<>(set);
    }

    static <E> Map<E, E> newMapFromSet(Set<E> set) {
        Objects.requireNonNull(set, "Set cannot be null");
        return new AbstractMap<E, E>() {
            @Override
            public Set<Entry<E, E>> entrySet() {
                return new AbstractSet<Entry<E, E>>() {
                    @Override
                    public Iterator<Entry<E, E>> iterator() {
                        return set.stream()
                            .map(e -> Map.entry(e, e))
                            .iterator();
                    }

                    @Override
                    public int size() {
                        return set.size();
                    }
                };
            }

            @SuppressWarnings("unchecked")
			@Override
            public E get(Object key) {
                if (key == null) {
                    throw new NullPointerException();
                }
                return set.contains(key) ? (E) key : null;
            }

            @SuppressWarnings("unchecked")
			@Override
            public E getOrDefault(Object key, E defaultValue) {
                if (key == null) {
                    throw new NullPointerException();
                }
                return set.contains(key) ? (E) key : defaultValue;
            }

            @Override
            public boolean containsKey(Object key) {
                if (key == null) {
                    throw new NullPointerException();
                }
                return set.contains(key);
            }

            @Override
            public int size() {
                return set.size();
            }
        };
    }
}