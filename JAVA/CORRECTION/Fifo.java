package fr.uge.fifo;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public final class Fifo<E> extends AbstractQueue<E>{
    private E[] elements;
    private int head;
    private int tail;
    private int size;

    @SuppressWarnings("unchecked")
    public Fifo(int capacity){
        if(capacity < 1){
            throw new IllegalArgumentException();
        }
        elements = (E[]) new Object[capacity];
        super();
    }
    @SuppressWarnings("unchecked")
    public Fifo(){
        elements = (E[]) new Object[16];
        super();
    }
    public int size(){
        return size;
    }
    public boolean offer(E element){
        Objects.requireNonNull(element);
        resize();
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        size++;
        return true;
    }
    public E poll(){
        if(size == 0){
            return null;
        }
        var element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return element;
    }
    public E peek(){
        if(size == 0){
            return null;
        }
        return elements[head];
    }
    @SuppressWarnings("unchecked")
    private void resize(){
        if(size == elements.length){
            var oldTab = Arrays.copyOf(elements, elements.length);
            elements = (E[]) new Object[elements.length * 2];
            System.arraycopy(oldTab, head, elements, 0, size - head);
            System.arraycopy(oldTab, 0, elements, size - head, size - tail);
            head = 0;
            tail = size;
        }
    }
    @Override
    public String toString(){
        var joiner = new StringJoiner(", ", "[", "]");
        for(var i = 0; i < size; i++){
            joiner.add(String.valueOf(elements[(head + i) % elements.length]));
        }
        return joiner.toString();
    }
    @Override
    public Iterator<E> iterator(){
        return new Iterator<>() {
            private int start = head;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }
            @Override
            public E next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                var element = elements[start];
                start = (start + 1) % elements.length;
                count++;
                return element;
            }
        };
    }
}