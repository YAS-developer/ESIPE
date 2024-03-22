package fr.uge.manisfest;

import java.util.Objects;

public record Container(String destination, int weight) implements Transportable{
  public Container{
      Objects.requireNonNull(destination, "Destination required");
      if(weight < 0){
          throw new IllegalArgumentException("Weight must be positive.");
      }
  }

  @Override
  public int price(){
      return this.weight*2;
  }
  
  @Override
  public String toString(){
      return this.destination+" "+this.weight+"kg";
  }
}
