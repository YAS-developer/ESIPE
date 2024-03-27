package fr.uge.manifest;

import java.util.Objects;

import fr.uge.manisfest.Transportable;

import java.lang.IllegalArgumentException;

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
    public int weight(){
        return this.weight;
    }
    

    @Override
    public String destination(){
        return this.destination;
    }

    @Override
    public boolean isContainer(){
        return true;
    } 

    @Override
    public String toString(){
        return this.destination+" "+this.weight+"kg";
    }
}