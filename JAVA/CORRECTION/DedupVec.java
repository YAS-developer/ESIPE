package fr.uge.dedup;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;

public final class DedupVec<E> extends AbstractList<E> implements RandomAccess {
  private final ArrayList<E> list = new ArrayList<>();
  private final HashMap<E, E> map = new HashMap<>();


  public DedupVec(){

  }

  DedupVec(Set<? extends E> set, Map<? extends E, ? extends E> map){
    list.addAll(set);
    this.map.putAll(map);
  }

  public int size() {
    return list.size();
  }

  public boolean add(E element) {
    Objects.requireNonNull(element);

    var elem = map.computeIfAbsent(element, _ -> element);
    list.add(elem);

    return true;

  }

  public E get(int index) {
    return list.get(index);
  }

  public boolean contains(Object element) {
    Objects.requireNonNull(element);
    return map.containsKey(element);
  }

  //  public void addAll(DedupVec<E> dedup) {
  //    Objects.requireNonNull(dedup);
  //    for(var element : dedup.list) {
  //      add(element);
  //    }
  //  }

  public boolean addAll(DedupVec<E> dedup) {
    Objects.requireNonNull(dedup);
    var hasDuplicate = false;
    for(var key : dedup.map.keySet()) {
      if(map.putIfAbsent(key, key)  != null) {
        hasDuplicate = true;
      }
    }

    if(!hasDuplicate) {
      list.addAll(dedup.list);
    }else {
      for(var element : dedup.list) {
        var e = map.get(element);
        list.add(e);
      }
    }
    return true;
  }

  public boolean addAll(Collection<? extends E> c) {

    var modified = false;
    for(var element : c) {
      this.add(element);
      modified = true;
    }
    return modified;

  }



  static <E> Map<E, E> newMapFromSet(Set<E> set) {
    return new AbstractMap<E, E>() {
      @Override
      public Set<Map.Entry<E, E>> entrySet() {

        return new AbstractSet<Map.Entry<E,E>>() {

          @Override
          public int size() {
            return set.size();
          }

          @Override
          public Iterator<Map.Entry<E, E>> iterator() {
            return new Iterator<>() {
              private final Iterator<E> iterator = set.iterator();

              @Override
              public boolean hasNext() {
                return iterator.hasNext();
              }

              @Override
              public Entry<E, E> next() {
                if(!hasNext()) {
                  throw new NoSuchElementException();
                }
                var e = iterator.next();
                Objects.requireNonNull(e);
                return new SimpleEntry<E,E>(e, e);
              }
            };
          }
        };
      }

      @Override
      public boolean containsKey(Object key) {
        Objects.requireNonNull(key);
        return set.contains(key);
      }

      @Override
      public E get(Object key) {
        Objects.requireNonNull(key);
        if(!containsKey(key)) return null;
        @SuppressWarnings("unchecked")
        var k = (E) key;
        return k;
      }

      @Override
      public E getOrDefault(Object key, E defaultValue) {
        Objects.requireNonNull(key);
        var elem = get(key);
        if(elem != null) {
          return elem;
        }
        return defaultValue;
      }
    };
  }

  static <E> DedupVec<E> fromSet(Set<? extends E> set){
    return new DedupVec<>(set, newMapFromSet(set));  
  }



}
