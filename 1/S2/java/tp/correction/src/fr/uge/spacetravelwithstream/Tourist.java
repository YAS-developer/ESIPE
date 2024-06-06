package fr.uge.spacetravelwithstream;

import java.util.Objects;

public record Tourist(String name, int age, boolean vip) implements Passenger {
  
	public Tourist {
		Objects.requireNonNull(name);
	}

	@Override
	public int maxHibernation() {
		return vip ? 60 : 50;
	}
	
	 @Override
	  public String toString() {
	    return "Tourist " + name +  " (" + age + " years" + (vip? " vip)": ")");
	  }
	 

}
