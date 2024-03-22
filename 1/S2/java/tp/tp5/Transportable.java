package fr.uge.manisfest;

public interface Transportable{
    int price();
    int weight();
    default boolean isContainer(){return false;}
}