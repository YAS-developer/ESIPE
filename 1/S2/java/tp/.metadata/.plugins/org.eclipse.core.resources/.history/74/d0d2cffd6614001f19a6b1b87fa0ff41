package info.esiee.tp8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record Actor(String firstName, String lastName) {
  public Actor {
    Objects.requireNonNull(firstName);
    Objects.requireNonNull(lastName);
  }
  
  
  public static Map<String, List<Actor>> actorGroupByFirstName(List<Actor> actors){
//  	var hm = new HashMap<String, List<Actor>>();
//  	for(var actor: actors) {
//    	hm.computeIfAbsent(actor.firstName(), k ->{
//    		if(hm.get(k) == null) {
//    			var list = new ArrayList<Actor>();
//    			list.add(actor);
//    			return list;
//    		}
//    		hm.get(k).add(actor);
//    		return hm.get(k);
//    	});
//    }
//  

      var groupedActors = new HashMap<String, List<Actor>>();
      for (Actor actor : actors) {
          groupedActors.computeIfAbsent(actor.firstName(), k -> new ArrayList<Actor>()).add(actor);
      }
      return groupedActors;
  
  }
}