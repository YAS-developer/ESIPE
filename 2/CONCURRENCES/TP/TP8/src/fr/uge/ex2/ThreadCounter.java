package fr.uge.ex2;

import java.io.*;

public class ThreadCounter {
 public static void main(String[] args) throws IOException {
   // Tableau pour stocker les références des threads
   Thread[] threads = new Thread[4];
   
   // Création et démarrage des 4 threads
   for (int i = 0; i < 4; i++) {
     final int id = i;  // pour utiliser dans le lambda
     threads[i] = Thread.ofPlatform().start(() -> {
       var counter = 0;
       while (!Thread.interrupted()) {
         try {
           Thread.sleep(1000);
           System.out.println("Thread " + id + ": " + counter++);
         } catch (InterruptedException e) {
           break;  // Sort de la boucle si interrompu
         }
       }
     });
   }

   // Lecture de l'entrée standard
   System.out.println("enter a thread id (0-3) or Ctrl-D to exit:");
   try (var input = new InputStreamReader(System.in);
        var reader = new BufferedReader(input)) {
     String line;
     while ((line = reader.readLine()) != null) {  // null si Ctrl-D
       try {
         var threadId = Integer.parseInt(line);
         if (threadId >= 0 && threadId < 4) {
           threads[threadId].interrupt();  // Interrompt le thread spécifié
         }
       } catch (NumberFormatException e) {
         System.out.println("Please enter a valid number (0-3)");
       }
       System.out.println("enter a thread id (0-3) or Ctrl-D to exit:");
     }
   }

   // Si Ctrl-D, interrompt tous les threads restants
   for (Thread t : threads) {
     if (t.isAlive()) {
       t.interrupt();
     }
   }
 }
}