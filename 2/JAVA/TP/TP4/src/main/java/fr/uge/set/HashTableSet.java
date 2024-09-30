package fr.uge.set;

import java.util.Objects;
import java.util.function.Consumer;

public final class HashTableSet<T> {
    private static final int INITIAL_CAPACITY = 16;
    private Object[] entries;
    private int size;

    public HashTableSet() {
        entries = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(T value) {
        Objects.requireNonNull(value);
        if (size >= entries.length / 2) {
            resize();
        }
        addInternal(value);
    }

    private void addInternal(T value) {
        int index = hash(value);
        for (var e = (Entry<T>) entries[index]; e != null; e = e.next()) {
            if (value.equals(e.value())) {
                return;
            }
        }
        entries[index] = new Entry<>(value, (Entry<T>) entries[index]);
        size++;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Object[] oldEntries = entries;
        entries = new Object[oldEntries.length * 2];
        size = 0;
        for (Object entry : oldEntries) {
            for (Entry<T> e = (Entry<T>) entry; e != null; e = e.next()) {
                addInternal(e.value());
            }
        }
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (Object entry : entries) {
            for (Entry<T> e = (Entry<T>) entry; e != null; e = e.next()) {
                action.accept(e.value());
            }
        }
    }

    public boolean contains(Object value) {
        Objects.requireNonNull(value, "Value cannot be null");
        int index = hash(value);
        for (var entry = (Entry<?>) entries[index]; entry != null; entry = entry.next()) {
            if (value.equals(entry.value())) {
                return true;
            }
        }
        return false;
    }

    private int hash(Object value) {
        return value.hashCode() & (entries.length - 1);
    }

    private static final record Entry<E>(E value, Entry<E> next) {
    }
}