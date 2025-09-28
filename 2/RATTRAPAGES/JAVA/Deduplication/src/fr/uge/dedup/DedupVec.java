package fr.uge.dedup;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class DedupVec<T> {
	
	
	private final ArrayList<T> list = new ArrayList<T>(); 
	private final HashMap<T, T> map = new HashMap<T, T>();
	
	public DedupVec(){
		
	}
	
	public void add(T item) {
		Objects.requireNonNull(item);
		
		var val = map.putIfAbsent(item, item);
		
		
		if(val == null) {
			list.add(item);
		}
		else {
			list.add(val);
		}
	}
	
	/*
	 * 	Point point1 = new Point(2, 3);
      	Point point2 = new Point(2, 3);
     
     	DedupVec<Point> dedupVec = new DedupVec<Point>();
      	dedupVec.add(point1);
      	dedupVec.add(point2);
	 * 
	 */
	
	
	public boolean contains(Object element) {
		Objects.requireNonNull(element);
		 
		return map.containsKey(element);
	}
	
	
	public void addAll(DedupVec<T> dedupVec) {
		Objects.requireNonNull(dedupVec);
		for(int i=0; i<dedupVec.size();i++) {
			this.add(dedupVec.get(i));
		}
		
	}
	
	
	public T get(int index) {
		Objects.checkIndex(index, list.size());
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}

	static<T> Map<T,T> newMapFromSet(Set<T> set){
		return new AbstractMap<T, T>(){

			@Override
			public Set<Entry<T, T>> entrySet() {
				return new AbstractSet<Map.Entry<T,T>>() {

					@Override
					public int size() { 
						return set.size();
					}

					@Override
					public Iterator<Entry<T, T>> iterator() {
						return new Iterator<Map.Entry<T,T>>() {
							private T ele;
							@Override
							public boolean hasNext() {
								
								return !set.isEmpty();
							}

							@Override
							public Entry<T, T> next() {
								
								set.forEach((element) ->{
									 ele = element;
								});
								Objects.requireNonNull(ele);
								set.remove(ele);
								return new AbstractMap.SimpleEntry<T,T>(ele, ele);
							}
						};
					}
					
				};
			}
			
		};
	}
	

}
