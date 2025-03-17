package fr.uge.conc.ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import fr.uge.conc.api.Answer;
import fr.uge.conc.api.Request;

public class CheapestPooled {

    private final String item;
    private final int timeoutMilliPerRequest;
    private final int poolSize;
    private final BlockingQueue<String> sitesQueue;
    private final BlockingQueue<Optional<Answer>> answersQueue;

    public CheapestPooled(String item, int timeoutMilliPerRequest, int poolSize) {
        Objects.requireNonNull(item);
        this.item = item;
        this.timeoutMilliPerRequest = timeoutMilliPerRequest;
        this.poolSize = poolSize;
        this.sitesQueue = new LinkedBlockingQueue<>(Request.getAllSites());
        this.answersQueue = new LinkedBlockingQueue<>();
    }

    public Optional<Answer> retrieve() throws InterruptedException {
        List<Thread> workers = new ArrayList<>();

        for (int i = 0; i < poolSize; i++) {
            var worker = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
//                        var site = sitesQueue.poll(timeoutMilliPerRequest, TimeUnit.MILLISECONDS);
                        var site = sitesQueue.take();
                        var request = new Request(site, item);
                        var answer = request.request(timeoutMilliPerRequest);
                        answersQueue.put(answer);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            });
            workers.add(worker);
            worker.start();
        }

        Optional<Answer> cheapestAnswer = Optional.empty();
        try {
            for (int i = 0; i < sitesQueue.size(); i++) {
//                var answer = answersQueue.poll(timeoutMilliPerRequest, TimeUnit.MILLISECONDS);
                var answer = answersQueue.take();
                if (answer.isPresent()) {
                    if (cheapestAnswer.isEmpty() || answer.get().price() < cheapestAnswer.get().price()) {
                        cheapestAnswer = answer;
                    }
                }
            }
        } finally {
            workers.forEach(Thread::interrupt);
        }

        return cheapestAnswer;
    }

    public static void main(String[] args) throws InterruptedException {
        var aggregator = new CheapestPooled("tortank", 2_000, 3);
        var answer = aggregator.retrieve();
        System.out.println(answer); // Optional[tortank@... : ...]
    }
}
