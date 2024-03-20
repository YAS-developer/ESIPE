import java.util.Objects;
import java.util.LinkedList;

public class Manifest{
    private final LinkedList<Container> containerList;

    public Manifest(){
        this.containerList = new LinkedList<Container>();
    }

    public void add(Container c){
        Object.requireNonNull(c, "Container must be not null");
        this.containerList.add(c);
    }


    public void display(){
        int i=0;
        var sb = new StringBuilder();
        for(var container: containerList){
            sb.append(i).append(" ").append(container.destination()).append(" ").append(container.destination()).append(" kg").append("\n");
        }

        System.out.print(sb);
    }
}