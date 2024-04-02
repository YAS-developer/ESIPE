import java.util.Scanner; 

public class Calc { 
  public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	int value = scanner.nextInt(); 
	int value2 = scanner.nextInt();
	if(value2 > value){
		System.out.println(
		  "Somme: "+(value+value2)+"\n"+
		  "Différence: "+(value2-value)+"\n"+
		  "Produit: "+(value*value2)+"\n"+
		  "Quotient: "+(value/value2)+"\n"+
		  "Reste: "+(value%value2)+"\n"
		);
	}
	else{
		System.out.println(
		  "Somme: "+(value+value2)+"\n"+
		  "Différence: "+(value-value2)+"\n"+
		  "Produit: "+(value*value2)+"\n"+
		  "Quotient: "+(value/value2)+"\n"+
		  "Reste: "+(value%value2)+"\n"
		);
	}
  }
}