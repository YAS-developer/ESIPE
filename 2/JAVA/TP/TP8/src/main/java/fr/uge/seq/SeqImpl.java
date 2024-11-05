package fr.uge.seq;

import java.util.List;

import java.util.StringJoiner;
import java.util.stream.Collectors;


final class SeqImpl<T, U> implements Seq<U>{

  private final List<T> list;
  private final Function<? super T, ? extends U> mapper;
   
  SeqImpl(List<T> list, Function<? super T, ? extends U> mapper) {
    Objects.requireNonNull(list);
    this.list = list;
    this.mapper = mapper;
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public U get(int index) {
    Objects.checkIndex(0, size());
    return mapper.apply(list.get(index));
  }
  
  @Override
  public String toString() {
    return list.stream()
        .map(mapper)
        .map(U::toString).collect(Collectors.joining(", ", "<", ">"));
  }

  @Override
  public <V> Seq<V> map(Function<? super U, ? extends V> function) {
   Objects.requireNonNull(function);
   return new SeqImpl<>(list, mapper.andThen(function));
  }
}