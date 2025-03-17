package esiee;

public class HelloThreadBis {
  
  public static void println(String s){
    for(var i = 0; i < s.length(); i++){
      System.out.print(s.charAt(i));
    }
    System.out.print("\n");
  }
  
  
  public static void main(String[] args) {
    for(var i = 0; i < 150; i++) {
      var oldI = i;
       Thread.ofPlatform().start(() -> {
         
         for(var j = 0; j <= 5000; j++) {
           HelloThreadBis.println("hello " + oldI + " " + j);
         }
       });
    }
  }
}


/** 
Exercice 4

2. Expliquer le comportement observé.

Le problème survient car les threads s'interrompent mutuellement pendant qu'ils impriment leurs chaînes de caractères. Un thread commence à afficher une partie du texte, mais l'ordonnanceur peut décider de passer à un autre thread avant que l'impression ne soit terminée. Cela provoque un mélange de caractères à l'écran, car plusieurs threads écrivent en même temps dans la console.

3. Pourquoi ce comportement n’apparaît-il pas quand on utilise System.out.println ?

Ce comportement n'apparaît pas avec System.out.println() car cette méthode est synchronisée. Cela signifie qu'elle garantit qu'un seul thread peut accéder à la console à la fois pour imprimer une ligne entière, empêchant ainsi les interruptions par d'autres threads pendant l'affichage.
 
**/
