package fr.uge.conc.ex2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

import fr.uge.conc.api.Answer;
import fr.uge.conc.api.Request;

public class Cheapest {

    private final String item;
    private final int timeoutMilliPerRequest;

    public Cheapest(String item, int timeoutMilliPerRequest) {
        Objects.requireNonNull(item);
        this.item = item;
        this.timeoutMilliPerRequest = timeoutMilliPerRequest;
    }

    public Optional<Answer> retrieve() throws InterruptedException {

        var queue = new SynchronousQueue<Optional<Answer>>(); // On recevra toutes les réponses, bonnes ou non
        var threadList = new ArrayList<Thread>();
        var sites = Request.getAllSites();
        var allResponses = new ArrayList<Answer>();

        for (var site : sites) {
            Thread.ofPlatform().name("Thread-" + site).start(() -> {
                threadList.add(Thread.currentThread());
                try {
                    var request = new Request(site, item);
                    var response = request.request(timeoutMilliPerRequest);
                    response.ifPresent(allResponses::add); // Ajouter la réponse si elle est présente
                } catch (InterruptedException e) {
                    return;
                }
            });
        }

        try {
            for (var i = 0; i < threadList.size(); i++) {
                // Collecte toutes les réponses
                var response = queue.take();
                response.ifPresent(allResponses::add);
            }
            
            // Retourne l'option avec le prix le moins cher si des réponses existent
            return allResponses.stream().min(Comparator.comparing(Answer::price));

        } finally {
            threadList.forEach(Thread::interrupt); // On interrompt tout à la fin
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var aggregator = new Cheapest("tortank", 2_000);
        var answer = aggregator.retrieve();
        System.out.println(answer);
    }
}
