package fr.uge.manifest;

import java.util.Objects; 
import java.lang.IllegalArgumentException;

public record Container(String destination, int weight){
    public Container{
        Objects.requireNonNull(destination, "Destination required");
        if(weight < 0){
            throw new IllegalArgumentException("Weight must be positive.");
        }
    }
}