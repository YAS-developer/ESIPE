package fr.uge.concurrence.exo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

public class PokemonFactory {
  public static void main(String[] args) {
    var capturedPokemon = new ArrayBlockingQueue<PokeAPI.Pokemon>(50);
    var raretyNormal = new ArrayBlockingQueue<PokeAPI.Pokemon>(50);
    var raretyRare = new ArrayBlockingQueue<PokeAPI.Pokemon>(50);
    var valueToPokeballs = new HashMap<Integer, ArrayBlockingQueue<PokeAPI.Pokeball>>();

    // Initialisation de la valueToPokeballs
    IntStream.rangeClosed(0, 10).forEach(value -> {
      valueToPokeballs.put(value, new ArrayBlockingQueue<>(50));
    });

    // 5 Thread de capture
    IntStream.range(0, 5).forEach(threadCapture -> {
      Thread.ofPlatform().start(() -> {
        for (;;) {
          try {
            var pokemon = PokeAPI.capture();
            capturedPokemon.put(pokemon);
          } catch (InterruptedException e) {
            throw new AssertionError(e);
          }
        }
      });
    });

    // Thread routeur : dispatch vers les bonnes files
    Thread.ofPlatform().start(() -> {
      for (;;) {
        try {
          var pokemon = capturedPokemon.take();
          if (pokemon.rarity() == 5) {
            raretyRare.put(pokemon);
          } else {
            raretyNormal.put(pokemon);
          }
        } catch (InterruptedException e) {
          throw new AssertionError(e);
        }
      }
    });

    // Thread de trapping pour rareté 0-4
    Thread.ofPlatform().start(() -> {
      for (;;) {
        try {
          var pokemon = raretyNormal.take();
          var pokeball = PokeAPI.trap(pokemon);
          valueToPokeballs.get(pokeball.value()).put(pokeball);
        } catch (InterruptedException e) {
          throw new AssertionError(e);
        }
      }
    });

    // Thread de trapping pour rareté 5
    Thread.ofPlatform().start(() -> {
      for (;;) {
        try {
          var pokemon = raretyRare.take();
          var pokeball = PokeAPI.trap(pokemon);
          valueToPokeballs.get(pokeball.value()).put(pokeball);
        } catch (InterruptedException e) {
          throw new AssertionError(e);
        }
      }
    });

    // 2 Threads par valeur 0-10 pour créer des Crates de 5 Pokeballs
    IntStream.rangeClosed(0, 10).forEach(value -> {
      Runnable crateBuilder = () -> {
        var buffer = new ArrayList<PokeAPI.Pokeball>(5);
        var queue = valueToPokeballs.get(value);
        for (;;) {
          try {
            buffer.add(queue.take());
            if (buffer.size() == 5) {
              var crate = PokeAPI.box(List.copyOf(buffer));
              System.out.println(crate);
              buffer.clear();
            }
          } catch (InterruptedException e) {
            throw new AssertionError(e);
          }
        }
      };
      Thread.ofPlatform().start(crateBuilder);
      Thread.ofPlatform().start(crateBuilder);
    });
  }
}
