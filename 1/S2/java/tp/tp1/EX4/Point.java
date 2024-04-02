import java.util.Objects;

public record Point(int x, int y){
  public Point{
    Objects.requireNonNull(x, "x doit être renseigner.");
    Objects.requireNonNull(y, "y doit être renseigner.");
  }

  public double dimension(Point p){
    double dx = this.x - p.x;
    double dy = this.y - p.y;
    return Math.sqrt(dx * dx + dy * dy);
  }

  public static void main(String[] args){  
    try{
      int x = Integer.parseInt(args[0]);
      int y = Integer.parseInt(args[1]);

      var point = new Point(x, y);
      var origine = new Point(0, 0);
       
      System.out.println("x: "+point.x()+" y: "+point.y());

      System.out.println("Distance du point à l'origine: " + point.distance(origine));
    }
    catch(Exception e){
      throw new IllegalArgumentException("Veuillez mettre deux entiers");
    }
  }
}




