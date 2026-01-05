package fr.uge.unorderedvec;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class UnorderedVec <E> implements Iterable<E> {
	
	private final E tab[];
	private int size;
	
	@SuppressWarnings("unchecked")
	public UnorderedVec() {
		super();
		tab = (E[]) new Object[16];
	}
	
	
	public void add(E element) {
		Objects.requireNonNull(element);
		Objects.checkFromIndexSize(0, size, 16);
		tab[size++]= element;
	}
	
	
	public int size() {
		return size;
	}
	

	
	private static int start(int size) {
        return size == 0 ? 0 : (int) ((size * 0x5DEECE66DL + 11) & 0x7FFFFFFF) % size;
    }


	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>(){
			private final int startIndex = start(size);
			private int count=0;
			@Override
			public boolean hasNext() {
				return count < size;
			}

			@Override
			public E next() {
				if(!hasNext()) {throw new NoSuchElementException();}
				
				int index = (startIndex + count) % size;
	            count++;
				return tab[index];
			}
			
		};
	}
}
