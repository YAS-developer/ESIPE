package fr.uge.mardaci.advanced_java.fifo;

import java.util.*;

public final class Fifo<T> extends AbstractQueue<T> implements Iterable<T> {
  private int capacity;
  private T[] array;
  private int size = 0;
  private int head = 0;
  private int tail = 0;

  public Fifo(int capacity) {
    if (capacity < 1) {
      throw new IllegalArgumentException();
    }
    this.capacity = capacity;
    @SuppressWarnings("unchecked")
    var array = (T[]) new Object[capacity];
    this.array = array;
  }

  public Fifo() {
    this(16);
  }

  private void resize() {
    var newCapacity = capacity << 1;
    @SuppressWarnings("unchecked")
    var newArray = (T[]) new Object[newCapacity];
    for (int i = 0; i < size; i++) {
      newArray[i] = array[(head + i) % capacity];
    }
    this.capacity = newCapacity;
    this.array = newArray;
    this.head = 0;
    this.tail = size;
  }

  @Override
  public boolean offer(T value) {
    Objects.requireNonNull(value);
    if (size >= capacity) {
      resize();
    }
    array[tail] = value;
    tail = (tail + 1) % capacity;
    size++;
    return true;
  }

  @Override
  public T poll() {
    if (size == 0) {
      return null;
    }
    var value = array[head];
    array[head] = null;
    head = (head + 1) % capacity;
    size--;
    return value;
  }

  @Override
  public T peek() {
    if (size == 0) {
      return null;
    }
    return array[head];
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<>() {
      private int iterationCount = 0;
      private int head = Fifo.this.head;
      private final int size = Fifo.this.size;
      private final int capacity = Fifo.this.capacity;

      @Override
      public boolean hasNext() {
        return iterationCount < size;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        var value = array[head];
        head = (head + 1) % capacity;
        iterationCount++;
        return value;
      }
    };
  }

  @Override
  public String toString() {
    if (size == 0) {
      return "[]";
    }
    var stringJoiner = new StringJoiner(", ", "[", "]");
    if (head == tail) {
      for (var i = head; i < capacity; i++) {
        stringJoiner.add(array[i].toString());
      }
      for (var i = 0; i < tail; i++) {
        stringJoiner.add(array[i].toString());
      }
    } else {
      for (var i = head; i < tail; i++) {
        stringJoiner.add(array[i].toString());
      }
    }
    return stringJoiner.toString();
  }
}
