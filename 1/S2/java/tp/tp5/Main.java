package fr.uge.manifest.main;


public class Main{
    public static void main(String args){
        var container = new Container("Germany", 500);
        System.out.println(container.destination());  // Germany
        System.out.println(container.weight());  // 500
    }
}