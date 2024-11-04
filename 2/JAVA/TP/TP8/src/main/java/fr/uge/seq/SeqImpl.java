package fr.uge.seq;

import java.util.List;

import java.util.StringJoiner;
import java.util.stream.Collectors;


final class SeqImpl<T> implements Seq<T>{
  private final List<T> elements;
  
  
  
  SeqImpl(List<T> elements) {
    this.elements = elements;
  }
  
  @Override
  public int size() {
    return elements.size();
  }
  
  @Override
  public T get(int index) {
    return elements.get(index);
  }
  
  
  @Override
  public String toString() {
    return elements.stream().map(e -> e.toString()).collect(Collectors.joining(", ", "<", ">"));
  }
 
}