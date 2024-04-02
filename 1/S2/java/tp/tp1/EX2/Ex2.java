import java.util.MissingFormatArgumentException;

public class Ex2{
  public static void main(String[] args){
    if(args.length == 0){
      throw new MissingFormatArgumentException("Veuillez insérer au moins un argumments");
    }

    // for(int i=0; i<args.length; i++){
    //   System.out.println(args[i]);
    // }
    for(var arg: args){
      System.out.println(arg);
    }
  }
}