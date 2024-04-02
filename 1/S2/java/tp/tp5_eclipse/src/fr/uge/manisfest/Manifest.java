package fr.uge.manisfest;

import java.util.Objects;
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

    @Override
    public String toString(){
        int i=0;
        var sb = new StringBuilder();
        for(var transported: TransportableList){ 
            sb.append(i).append(" ").append(transported.toString()).append("\n");
            i++;
        }
        return sb.toString();
    }

}
