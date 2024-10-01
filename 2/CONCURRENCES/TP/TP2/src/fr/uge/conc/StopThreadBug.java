package fr.uge.conc;

public class StopThreadBug implements Runnable {
	  private boolean stop = false;

	  public void stop() {
	    stop = true;
	  }

	  @Override
	  public void run() {
	    while (!stop) {
	      System.out.println("Up");
	    }
	    System.out.print("Done");
	  }

	  public static void main(String[] args) throws InterruptedException {
	    var stopThreadBug = new StopThreadBug();
	    Thread.ofPlatform().start(stopThreadBug::run);
	    Thread.sleep(1_000);
	    System.out.println("Trying to tell the thread to stop");
	    stopThreadBug.stop();
	  }
	  
	  
	}

	//2 On a les up puis les trying puis quelques potentiels up puis le down donc c'est "normal"
	

	//3 le jit (et non le bytecode) transforme le run avec l'ajout d'une variable globale pour optimiser le code
	// mais du coup la varibale n'est jamais mis a true donc le programme ne s'arrete jamais

	//4 Non car c'est pas parcque le jit n'optimise pas le code sur nons machines qui ne peut pas le faire