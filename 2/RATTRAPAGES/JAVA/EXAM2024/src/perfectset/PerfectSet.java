package perfectset;

import java.util.Objects;
import java.util.function.ToIntFunction;


final class PerfectSet<T> {
	private  int capacity;
	private int size;
	private final ToIntFunction<T> function;
	private T tab[];
	
	@SuppressWarnings("unchecked")
	public PerfectSet(int capacity, ToIntFunction<T> function) {
		Objects.requireNonNull(function);
		if(capacity <= 0) {
			throw new IndexOutOfBoundsException("capacity must be positive");
		}
		this.size=0;
		this.capacity = capacity;
		this.tab = (T[]) new Object[capacity];
		this.function = function;
	}
	
	
	
	
	public void add(T element) {
		Objects.requireNonNull(element);
		if(size >= capacity) {
			throw new IndexOutOfBoundsException("tab is full");
		}
		int nb=function.applyAsInt(element);
		if(nb < 0 || nb >= capacity) {
			throw new IllegalArgumentException("Element not accepted");
		}
	
		this.tab[nb] = element;
		size++;
	}
	
	public boolean contains(T element) {
		Objects.requireNonNull(element);
		int nb=function.applyAsInt(element);
		if(nb < 0 || nb >= capacity) {
			return false;
		}
		
		if(this.tab[nb].equals(element)) {
			return true;
		}
		return false;
	}
	
	
	public int size() {
		return this.size;
	}
}
