package fr.uge.concurrence.exo2;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CheapestWithAllItems {
    private final int timeoutMilliPerRequest;
    private final int timeoutMilliGlobal;
    private final int poolSize;

    public CheapestWithAllItems(int timeoutMilliPerRequest, int poolSize, int timeoutMilliGlobal) {
        this.timeoutMilliPerRequest = timeoutMilliPerRequest;
        this.timeoutMilliGlobal = timeoutMilliGlobal;
        this.poolSize = poolSize;
    }

    private record Response(String site, Optional<Answer> answer){}

    public List<Answer> retrieve(List<String> items) throws InterruptedException {
        Objects.requireNonNull(items);
        var executorService = Executors.newFixedThreadPool(poolSize);
        var sites = Request.getAllSites();
        // Callables
        var callables = new ArrayList<Callable<Response>>();
        for (var site : sites) {
            for (var item : items) {
                callables.add(() -> {
                    var request = new Request(site, item);
                    var anwser = request.request(timeoutMilliPerRequest);
                    return new Response(site, anwser);
                });
            }
        }

        // Futures
        var futures = executorService.invokeAll(callables, timeoutMilliGlobal, TimeUnit.MILLISECONDS);
        var siteAnswers = new HashMap<String, List<Answer>>();
        for (var future : futures) {
            switch (future.state()) {
                case SUCCESS -> {
                    var result = future.resultNow();
                    if (result.answer().isPresent()) {
                        siteAnswers.computeIfAbsent(result.site(), _ -> new ArrayList<Answer>()).add(result.answer().get());
                    }
                }
            }
        }

        executorService.shutdown();

        // Vérifications
        for (var site : sites) {
            if (siteAnswers.get(site).size() < items.size()) {
                System.out.println(site + " did not respond in time.");
            }
        }

        return siteAnswers.values().stream().filter(set -> set.size() == items.size())
                .min(Comparator.comparingInt(set -> set.stream().mapToInt(Answer::price).sum()))
                .orElse(List.<Answer>of());
    }

    public static void main(String[] args) {
        try {
            var aggregator = new CheapestWithAllItems(2_000, 10, 5_000);
            var answer = aggregator.retrieve(List.of("tortank", "pikachu", "evoli"));
            System.out.println(answer);
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }
}