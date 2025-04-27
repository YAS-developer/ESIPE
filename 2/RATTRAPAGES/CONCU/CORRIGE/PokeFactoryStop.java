package fr.uge.concurrence.exo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class PokeFactoryStop {
  public static void main(String[] args) {
    var capturedPokemon = new ArrayBlockingQueue<PokeAPI.Pokemon>(50);
    var raretyNormal = new ArrayBlockingQueue<PokeAPI.Pokemon>(50);
    var raretyRare = new ArrayBlockingQueue<PokeAPI.Pokemon>(50);
    var valueToPokeballs = new HashMap<Integer, ArrayBlockingQueue<PokeAPI.Pokeball>>();
    var stop = new AtomicBoolean(false);

    // Initialisation de la valueToPokeballs
    IntStream.rangeClosed(0, 10).forEach(value -> {
      valueToPokeballs.put(value, new ArrayBlockingQueue<>(50));
    });

    // 5 Thread de capture
    IntStream.range(0, 5).forEach(threadCapture -> {
      Thread.ofPlatform().start(() -> {
        while (!stop.get()) {
          try {
            var pokemon = PokeAPI.capture();
            capturedPokemon.put(pokemon);
          } catch (InterruptedException e) {
            return;
          }
        }
      });
    });

    // Thread routeur : dispatch vers les bonnes files
    Thread.ofPlatform().start(() -> {
      while (!stop.get()) {
        try {
          var pokemon = capturedPokemon.take();
          if (pokemon.rarity() == 5) {
            raretyRare.put(pokemon);
          } else {
            raretyNormal.put(pokemon);
          }
        } catch (InterruptedException e) {
          return;
        }
      }
    });

    // Thread de trapping pour rareté 0-4
    Thread.ofPlatform().start(() -> {
      while (!stop.get()) {
        try {
          var pokemon = raretyNormal.take();
          var pokeball = PokeAPI.trap(pokemon);
          valueToPokeballs.get(pokeball.value()).put(pokeball);
        } catch (InterruptedException e) {
          return;
        }
      }
    });

    // Thread de trapping pour rareté 5
    Thread.ofPlatform().start(() -> {
      while (!stop.get()) {
        try {
          var pokemon = raretyRare.take();
          var pokeball = PokeAPI.trap(pokemon);
          valueToPokeballs.get(pokeball.value()).put(pokeball);
        } catch (InterruptedException e) {
          return;
        }
      }
    });

    // 2 Threads par valeur 0-10 pour créer des Crates de 5 Pokeballs
    IntStream.rangeClosed(0, 10).forEach(value -> {
      Runnable crateBuilder = () -> {
        var buffer = new ArrayList<PokeAPI.Pokeball>(5);
        var queue = valueToPokeballs.get(value);
        while (!stop.get()) {
          try {
            buffer.add(queue.take());
            if (buffer.size() == 5) {
              var crate = PokeAPI.box(List.copyOf(buffer));
              System.out.println(crate);
              buffer.clear();
              if (value == 10) {
                stop.set(true);
                Thread.currentThread().getThreadGroup().interrupt();
              }
            }
          } catch (InterruptedException e) {
            if (!buffer.isEmpty()) {
              PokeAPI.Crate crate = null;
              try {
                crate = PokeAPI.box(List.copyOf(buffer));
              } catch (InterruptedException ex) {
                throw new AssertionError(e);
              }
              System.out.println("Dernière Crate incomplète: " + crate);
              buffer.clear();
              return;
            }
          }
        }
      };
      Thread.ofPlatform().start(crateBuilder);
      Thread.ofPlatform().start(crateBuilder);
    });
  }
}
