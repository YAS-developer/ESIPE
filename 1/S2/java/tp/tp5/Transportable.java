package fr.uge.manisfest;

public interface Transportable{
    int price();
    int weight();
    String destination();
    default boolean isContainer(){return false;}
}