package fr.uge.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CheapestPooled {
    private final String item;
    private final ExecutorService executorService;

    public CheapestPooled(String item) {
        this.item = item;
        this.executorService = Executors.newFixedThreadPool(Request.getAllSites().size() + 1);
    }

    public Optional<Answer> retrieve(int timeoutMilli) throws InterruptedException {
        List<Callable<Optional<Answer>>> tasks = new ArrayList<>();
        for (String site : Request.getAllSites()) {
            tasks.add(() -> new Request(site, item).request(timeoutMilli));
        }

        List<Future<Optional<Answer>>> futures = executorService.invokeAll(tasks);

        Optional<Answer> cheapest = Optional.empty();
        for (var future : futures) {
            try {
                Optional<Answer> answer = future.get();
                if (answer.isPresent()) {
                    if (cheapest.isEmpty() || answer.get().price() < cheapest.get().price()) {
                        cheapest = answer;
                    }
                }
            } catch (ExecutionException e) {
                // Ignore exceptions since we assume the API doesn't throw any
            }
        }

        executorService.shutdown();
        return cheapest;
    }
}