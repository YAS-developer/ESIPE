package fr.uge.data;

import java.util.function.Consumer;

public class LinkedLink<T> {
    private Link<T> link;
    private int size;

    public void add(T value) {
        link = new Link<>(value, link);
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Link<T> current = link;
        for (int i = 0; i < index; i++) {
            current = current.next();
        }
        return current.value();
    }

    public void forEach(Consumer<T> lambda) {
        Link<T> current = link;
        while (current != null) {
            lambda.accept(current.value());
            current = current.next();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Link<T> current = link;
        while (current != null) {
            sb.append(current.value());
            if (current.next() != null) {
                sb.append(" --> ");
            }
            current = current.next();
        }
        return sb.toString();
    }

    public void removeIf(Predicate<T> predicate) {
    while (link != null && predicate.test(head.value())) {
        link = link.next();
        size--;
    }
    
    Link<T> current = link;
    while (current != null && current.next() != null) {
        if (predicate.test(current.next().value())) {
            current.next = current.next().next();
            size--;
        } else {
            current = current.next();
        }
    }
    }
}
