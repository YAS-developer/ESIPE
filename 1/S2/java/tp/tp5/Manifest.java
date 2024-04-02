package fr.uge.manisfest;

import java.util.Objects;

import fr.uge.manifest.Container;

import java.util.LinkedList;

public class Manifest{
    private final LinkedList<Transportable> TransportableList;

    public Manifest(){
        this.TransportableList = new LinkedList<Transportable>();
    }

    public void add(Transportable t){
        Objects.requireNonNull(t, "Transport must be not null");
        this.TransportableList.add(t);
    }

    public int price(){
        int sum=0;
        for(var transported: TransportableList){ 
            sum += transported.price();
        }
        return sum;
    }

    public int weight(){
        int sum=0;
        for(var transported: TransportableList){
            if(transported instanceof Container){
                Container container = (Container) transported;
                sum += container.weight();
            }
        }
        return sum;
    }

    public void removeAllContainersFrom(String dest){
        Objects.requireNonNull(dest, "Destination required");
        
        var iterator = manifest.iterator();
        while(iterator.hasNext()) {
            var c = iterator.next();
            if(c.isContainer() && c.destination().equals(destination)) {
                iterator.remove();
            }
        }

    }

    @Override
    public String toString(){
        int i=1;
        var sb = new StringBuilder();
        for(var transported: TransportableList){ 
            sb.append(i).append(" ").append(transported.toString()).append("\n");
            i++;
        }
        return sb.toString();
    }

}