package fr.uge.seq;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public interface Seq<T> {
  int size();
  T get(int index);
  
  <R> Seq<R> map(Function<? super T, ? extends R> mapper);
  
  static <T> Seq<T> from(List<? extends T> list) {
    list.forEach(element -> Objects.requireNonNull(element, "elements cannot be null"));
    return new SeqImpl<>(List.copyOf(list));
  }
}
