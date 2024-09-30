package fr.uge.set;

import java.util.Objects;
import java.util.function.Consumer;

public final class HashTableSet {
    private static final int INITIAL_CAPACITY = 16;
    private Entry[] entries;
    private int size;

    public HashTableSet() {
        entries = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(Object value) {
        Objects.requireNonNull(value);
        if (size >= entries.length / 2) {
            resize();
        }
        addInternal(value);
    }

    private void addInternal(Object value) {
        int index = hash(value);
        for (var e = entries[index]; e != null; e = e.next) {
            if (value.equals(e.value)) {
                return;
            }
        }
        entries[index] = new Entry(value, entries[index]);
        size++;
    }

    private void resize() {
        Entry[] oldEntries = entries;
        entries = new Entry[oldEntries.length * 2];
        size = 0;
        for (Entry entry : oldEntries) {
            for (Entry e = entry; e != null; e = e.next) {
                addInternal(e.value);
            }
        }
    }

    public int size() {
        return size;
    }

    public void forEach(Consumer<? super Object> action) {
        Objects.requireNonNull(action);
        for (Entry entry : entries) {
            for (Entry e = entry; e != null; e = e.next) {
                action.accept(e.value);
            }
        }
    }

    public boolean contains(Object value) {
        Objects.requireNonNull(value, "Value cannot be null");
        int index = hash(value);
        for (var entry = entries[index]; entry != null; entry = entry.next) {
            if (value.equals(entry.value)) {
                return true;
            }
        }
        return false;
    }

    private int hash(Object value) {
        return value.hashCode() & (entries.length - 1);
    }

    private static record Entry(Object value, Entry next) {
    }
}