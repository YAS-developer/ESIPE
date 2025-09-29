package perfectset;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToIntFunction;

public final class PerfectSet<E> extends AbstractSet<E> {

  private final ToIntFunction<? super E> function;
  private final E[] tab;
  private int size;

  @SuppressWarnings("unchecked")
  public PerfectSet(int capacity, ToIntFunction<? super E> function) {
    Objects.requireNonNull(function);
    if(capacity < 0) {
      throw new IllegalArgumentException();
    }
    this.function = function;
    this.tab = (E[]) new Object[capacity];
  }

  public boolean add(E element) {
    Objects.requireNonNull(element);
    var hash = function.applyAsInt(element);
    if(hash < 0 || hash > tab.length -1) {
      throw new IndexOutOfBoundsException();
    }
    if(contains(element)) {
      return false;
    }
    tab[hash] = element;
    size++;
    return true;
  }


  /* 2. ClassCastException */
  public boolean contains(Object obj) {
    Objects.requireNonNull(obj);
    @SuppressWarnings("unchecked")
    E element = (E) obj;
    var hash = function.applyAsInt(element);
    if(hash < 0 || hash > tab.length -1) {
      return false;
    }
    if(tab[hash] != null) {
      return true;
    }
    return false;
  }

  public int size() {
    return size;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      private int count = 0;
      private int index = -1;

      private int findNext(int pos) {

        while(pos < tab.length) {
          if(tab[pos] != null) {
            return pos;
          }
          pos++;
        }
        return pos;
      }

      @Override
      public boolean hasNext() {
        return count < size;
      }

      @Override
      public E next() {
        if(!hasNext()) {
          throw new NoSuchElementException();
        }

        if(index == -1) {
          index = findNext(0);
        }

        E element = tab[index];
        count++;
        if(count < size) {
          index = findNext(index + 1);
        }

        return element;
      }
    };
  }

  @Override
  public Object[] toArray() {
    if(size == tab.length) {
      return tab.clone();
    } 

    var array = new Object[size];
    var i = 0;
    for(var element : tab) {
      if(element != null) {
        array[i] = element;
        i++;
      }
    }
    return array;
  }

  Map<Integer, E> asIndexedMap(){
    return new AbstractMap<Integer, E>() {
      @Override
      public Set<Entry<Integer, E>> entrySet() {  
        return new AbstractSet<Map.Entry<Integer,E>>() {

          @Override
          public int size() {
            return size;
          }

          @Override
          public Iterator<Entry<Integer, E>> iterator() {
            return new Iterator<Entry<Integer,E>>() {

              private Iterator<E> iteratorSet = PerfectSet.this.iterator();

              @Override
              public boolean hasNext() {
                return iteratorSet.hasNext();
              }

              @Override
              public Entry<Integer, E> next() {
                if(!hasNext()) {
                  throw new NoSuchElementException();
                }

                var element = iteratorSet.next();
                var index = function.applyAsInt(element);
                return new AbstractMap.SimpleEntry<>(index, element);

              }
            };
          }

        };

      }


      public E get(int index) {
        if(index < 0 || index > tab.length-1) {
          return null;
        }

        if(tab[index] != null) {
          return tab[index];
        }
        return null;
      }

      @Override
      public E get(Object key) {
        Objects.requireNonNull(key);

        var index = switch(key) {
        case Integer i -> i;
        default -> null;
        };

        if(index == null || index < 0 || index > tab.length-1) {
          return null;
        }

        if(tab[index] != null) {
          return tab[index];
        }

        return null; 
      }

      @Override
      public E getOrDefault(Object key, E defaultValue) {
        Objects.requireNonNull(key);
        var element = get(key);
        if(element != null) {
          return element;
        }

        return defaultValue;
      }

      @Override
      public boolean containsKey(Object key) {
        Objects.requireNonNull(key);
        return get(key) != null;
      }
    };
  }

  public static <E extends Enum<E>> PerfectSet<E> fromEnum(Class<E> enumclass){
    Objects.requireNonNull(enumclass);
    if(!enumclass.isEnum()) {
      throw new ClassCastException();
    }

    E[] values = enumclass.getEnumConstants();
        
    var perfectSet = new PerfectSet<E>(values.length, Enum::ordinal);
    return perfectSet;
  }

}
