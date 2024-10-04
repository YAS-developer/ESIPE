package fr.uge.conc;

public class HonorBoard {
  private String firstName;
  private String lastName;
  
  public synchronized void set(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  
  public synchronized String firstName() {
      return firstName;
  }

  public synchronized String lastName() {
      return lastName;
  }
  
  @Override
  public synchronized String toString() {
    return firstName + ' ' + lastName;
  }
  
  public static void main(String[] args) {
    var board = new HonorBoard();

    Thread.ofPlatform().start(() -> {
      for(;;) {
        board.set("Mickey", "Mouse");
      }
    });
    
    Thread.ofPlatform().start(() -> {
      for(;;) {
        board.set("Donald", "Duck");
      }
    });
    
    Thread.ofPlatform().start(() -> {
      for(;;) {
//        System.out.println(board);
    	  System.out.println(board.firstName() + ' ' + board.lastName());
      }
    });
  }
}



/*
Accès concurrents non synchronisés : La méthode set() et toString() accèdent aux champs firstName et lastName sans synchronisation.

 */