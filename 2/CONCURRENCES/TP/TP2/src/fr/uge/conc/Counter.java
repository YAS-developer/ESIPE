package fr.uge.conc;

public class Counter {
	  private int value;

	  public void addALot() {
	    for (var i = 0; i < 100_000; i++) {
	      this.value++;
	    }
	  }

	  public static void main(String[] args) throws InterruptedException {
	    var counter = new Counter();
	    var thread1 = Thread.ofPlatform().start(counter::addALot);
	    var thread2 = Thread.ofPlatform().start(counter::addALot);
	    thread1.join();
	    thread2.join();
	    System.out.println(counter.value);
	  }
	}
	
//2 On devrait normalement avoir 200000 mais comme les deux threads accèdent au champs (lecture/ecriture) sur le tas, il peux y avoir des croisemments du au fait que l'instruction n'est pas atomique.
// Donc ici pour l'incrementation, dans le code assembleur cela correspond à 6 étapes
// et juste avant l'étape oû la valeur est changé dans le tas on peux etre interrompu par le sch et donc le deuxième thread peux par example faire les 5 premières étapes puis incrémenter la valeur dans le tas.
// Mais quand le sch repasse au premier thread, ce dernier va donc finir avec la 6ème instruction et réecrire 1 dans le tas.

//3 T1 lit 0
//T2 lit 0 et compte jusqu'a 99, il écrit 99 et est désch avant de lire
// T1 fait 1++ et il écrit 1
// T2 lit 1
// T1 lit 1 et compte jusqu'a 100, il écrit 100
// T2 fait son ++ et il écrit 2