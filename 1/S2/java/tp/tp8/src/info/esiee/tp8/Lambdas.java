package info.esiee.tp8;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Lambdas {

  public static void upperCaseAll(List<String> list) {
  	Objects.requireNonNull(list);
    list.replaceAll(s -> s.toUpperCase(Locale.ROOT));
  }
    
//  public static Map<String, Integer> occurrences(List<String> strings) {
//    var occurrences = new HashMap<String, Integer>();
//    strings.forEach(string -> occurrences.put(string, occurrences.getOrDefault(string, 0) + 1));
//    return occurrences;
//  }
  
//  public static Map<String, Integer> occurrences(List<String> strings) {
//    var occurrences = new HashMap<String, Integer>();
//    strings.forEach(string -> occurrences.merge(string, 1, (oldValue, value) -> oldValue + value));
//    return occurrences;
//  }
  
  public static Map<String, Integer> occurrences(List<String> strings) {
  	Objects.requireNonNull(strings);
    Map<String, Integer> occurrences = new HashMap<>();
    strings.forEach(string -> occurrences.merge(string, 1, Integer::sum));
    return occurrences;
  }
}
