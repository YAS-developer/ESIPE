package fr.uge.unordoredvec;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public final class UnorderedVec<E> extends AbstractCollection<E> {
    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public UnorderedVec(){
        elements = (E[]) new Object[16];
        super();
    }
    public boolean add(E element){
        Objects.requireNonNull(element);
        if(size > Integer.MAX_VALUE - 16){
            throw new OutOfMemoryError();
        }
        if(size >= elements.length){
            elements = Arrays.copyOf(elements, size * 2);
        }
        elements[size] = element;
        size++;
        return true;
    }
    public int size(){
        return size;
    }
    public boolean remove(Object value){
        Objects.requireNonNull(value);
        for(var i = 0; i < size; i++){
            if(elements[i].equals(value)){
                elements[i] = elements[size - 1];
                elements[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }
    private static int start(int size) {
        return size == 0 ? 0 : (int) ((size * 0x5DEECE66DL + 11) & 0x7FFFFFFF) % size;
    }
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int i = start(size);
            private int count = 0;
            @Override
            public boolean hasNext() {
                return count < size;
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                var element = elements[i];
                i = (i + 1) % size;
                count++;
                return element;
            }
        };
    }
    @Override
    public String toString(){
        var joiner = new StringJoiner(", ", "<", ">");
        int count = 0;
        for(var i = start(size); count < size; i = (i + 1) % size){
            joiner.add(String.valueOf(elements[i]));
            count++;
        }
        return joiner.toString();
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof UnorderedVec<?> unorderedVec){
            int count = 0;
            for(var i = start(size); count < size; i = (i + 1) % size){
                if(!elements[i].equals(unorderedVec.elements[i])){
                    return false;
                }
                count++;
            }
            return true;
        }
        return false;
    }
    @Override
    public int hashCode(){
        int hashCode = 0;
        int count = 0;
        for(var i = start(size); count < size; i = (i + 1) % size){
            hashCode += elements[i].hashCode();
            count++;
        }
        return hashCode;
    }
}