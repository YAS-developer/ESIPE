package fr.uge.manisfest;

import java.util.Objects;

public record Passenger(String destination) implements Transportable{
    public Passenger{
        Objects.requireNonNull(destination, "Destination required");
    }

    
    @Override
    public int price(){
        return 10;
    }
    
    @Override 
    public String toString(){
        return destination+" (passenger)";
    }
}