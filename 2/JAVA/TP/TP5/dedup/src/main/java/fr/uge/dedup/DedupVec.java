package fr.uge.dedup;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class DedupVec<T> {

  private final HashMap<T,T> map = new HashMap<>();

  //linkedlist car resize
  private final LinkedList<T> list = new LinkedList<>();

  public DedupVec() {
  }

  public int size(){
    return list.size();
  }

  public T get(int index){
    return list.get(index);
  }

  public void add(T elementToAdd){
    Objects.requireNonNull(elementToAdd);
    var value = map.putIfAbsent(elementToAdd, elementToAdd );
    if(value != null){
      list.add(value);
    }else{
      list.add(elementToAdd);
    }
  }

  public boolean contains(Object elementToCheck){
    Objects.requireNonNull(elementToCheck);
    return map.containsKey(elementToCheck);
  }

  public void addAll(DedupVec<T> elementsToAdd){
    Objects.requireNonNull(elementsToAdd);
    for(var element : elementsToAdd.list){
      add(element);
    }
  }

  static <E> Map<E, E> newMapFromSet(Set<E> baseSet){
    //On renvoie une map correspondant qui pour chaque element renvoit le couple K/V : SetValue -> SetValue
    //On renvoie une classe anonyme AbstractMap
      //Cette abstractMap a 2 méthodes à implementer : iterator() & size
     //Le but d'iterator() est d'obtenir un objet Iterator pour iterer sur notre map
       //On implémente cet Iterator avec une classe anonyme
       //Comme on veut que notre map soit une vue non modifiable, on va juste itérer sur le Set duquel on crée le Map correspondant
       //

    return new AbstractMap<E, E>() { //On doit renvoyer une AbstractMap = Map anonyme
      @Override
      public Set<Entry<E, E>> entrySet() {
        return new AbstractSet<Entry<E, E>>() {

          @Override
          public Iterator<Entry<E, E>> iterator() {
            var iterator = baseSet.iterator();
            return new Iterator<Entry<E, E>>() {
              @Override
              public boolean hasNext() {
                return iterator.hasNext();
              }

              @Override
              public Entry<E, E> next() {
                var next = iterator.next();
                return Map.entry(next, next);
              }
            };
          }

          @Override
          public int size() {
            return baseSet.size();
          }
        };
      }
    };
  }
}