package fr.uge.conc.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Collections;

public class HelloListFixedBetter {
    private static class ThreadSafeList<E> {
        private final List<E> list = new ArrayList<>();

        public synchronized void add(E element) {
            list.add(element);
        }
        
//        public synchronized int size() {
//            return list.size();
//        }
//        
        public synchronized List<E> getContent() {
            return List.copyOf(list); 
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var nbThreads = 4;
        var threads = new Thread[nbThreads];
        
        var threadSafeList = new ThreadSafeList<Integer>();

        IntStream.range(0, nbThreads).forEach(j -> {
            Runnable runnable = () -> {
                for (var i = 0; i < 5_000; i++) {
                    threadSafeList.add(i);
                }
            };
            threads[j] = Thread.ofPlatform().start(runnable);
        });

        for (var thread : threads) {
            thread.join();
        }

        List<Integer> content = threadSafeList.getContent();
        System.out.println("Contenu de la liste :");
        System.out.println(content);
        System.out.println("Taille de la liste : " + content.size());
    }
}