package fr.uge.numeric;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;

public final class NumericVec<T> {
	
	private long[] tab;
	private int size;
	private final ToLongFunction<T> into;
	private final LongFunction<T> from;
	
	private NumericVec(long[] longTab, int size, ToLongFunction<T> into, LongFunction<T> from){
		this.tab = longTab;
		this.size = size;
		this.into = into;
		this.from = from;
	}
	

	public static  NumericVec<Long> longs(long... longTab){
		Objects.requireNonNull(longTab);
		return new NumericVec<>(Arrays.copyOf(longTab, longTab.length), longTab.length, null, null);
	}
	
	public static NumericVec<Integer> ints(int... intTab){
		Objects.requireNonNull(null);
		var array = Arrays.stream(intTab).mapToLong(i->i).toArray();
		return new NumericVec<>(array, array.length, i -> i, i -> (int)i);
	}
	
	public static NumericVec<Double> doubles(double... doubleTab){
		Objects.requireNonNull(null);
		var array = Arrays.stream(doubleTab).mapToLong(i->Double.doubleToRawLongBits(i)).toArray();
		return new NumericVec<>(array, array.length, i -> Double.doubleToRawLongBits(i), i -> Double.longBitsToDouble(i));
	}
	
	T get(int index) {
		Objects.checkIndex(index, size);
		return from.apply(index);
	}
	
	int size() {
		return size;
	}
	
	
	void add(T newElement){
		Objects.requireNonNull(newElement);
		if(size == tab.length){
			tab = Arrays.copyOf(tab, Math.max(1, tab.length*2));
		}
			
		tab[size++] = into.applyAsLong(newElement);
	}


	
}
