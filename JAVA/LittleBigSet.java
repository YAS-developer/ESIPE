  package fr.uge.exam;
  
  import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
  import java.util.Iterator;
  import java.util.LinkedHashSet;
  import java.util.NoSuchElementException;
  import java.util.Objects;
import java.util.SequencedSet;
import java.util.Set;
  
  public final  class LittleBigSet<E> extends AbstractSet<E> implements SequencedSet<E> {
  
    private  E e1;
    private  E e2;
    private Set<E> set;
  
  
    public boolean add(E element) {
      Objects.requireNonNull(element);
  
      if(set == null) {
        if(e1 == null) {
          e1 = element;
          return true;
        }
        
        if(e1.equals(element)) {
          return false;
        }
        
        if(e2 == null) {
          e2 = element;
          return true;
        }
        
        if(e2.equals(element)) {
          return false;
        }
        
        set = new LinkedHashSet<>();
        set.add(e1);
        set.add(e2);
        
        e1 = null;
        e2 = null;
       return set.add(element);
        
      }
      
      return set.add(element);
  
  
    }
  
    public int size() {
      var size = 0;
      
      if(set != null) return set.size();
      
      if(e1 != null) size++;
      if(e2 != null) size++;
      return size;
    }
  
    public boolean contains(Object element) {
      Objects.requireNonNull(element);
      
      if(set != null) return set.contains(element);
  
      if(element.equals(e1) || element.equals(e2)) {
        return true;
      }
      return false;
    }
    
    public Iterator<E> iterator(){
      
      if(set != null) {
        return new Iterator<E>() {
          private final Iterator<E> iterator = set.iterator();
          @Override
          public boolean hasNext() {
            return iterator.hasNext();
          }
  
          @Override
          public E next() {
            return iterator.next();
          }
          
          @Override
          public void remove() {
            throw new UnsupportedOperationException();
          }
        };
      }
      
      return new Iterator<E>() {
        private int i = 0;
        
        public boolean hasNext() {
          if(i == 0 && e1 != null) {
            return true;
          }else if(i == 1 && e2 != null) {
            return true;
          }
          return false;
        }
  
        @Override
        public E next() {
          if(!hasNext()) {
            throw new NoSuchElementException();
          }
          E element;
          if(i == 0) {
            element = e1;
          }else {
            element = e2;
          }
          i++;
          return element;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
      };
    }
  
  
  
    @Override
    public String toString() {
      
      if(set != null) return set.toString();
      
      
      if(e1 != null && e2 != null) {
        return "["+e1+", "+e2+"]";
      }else if(e1 != null){
        return "["+e1+"]";
      }
  
      return "[]";
    }

    @Override
    public SequencedSet<E> reversed() {
      var reversedSet = new LittleBigSet<E>();
      var list = new ArrayList<E>();
      if(set != null) {
        set.forEach(list::add);
      }else {
        if(e1 != null) list.add(e1);
        if(e2 != null) list.add(e2);
      }
      Collections.reverse(list);
      
      list.forEach(reversedSet::add);
      
      return reversedSet;
    }
  
  
  }
