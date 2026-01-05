package fr.uge.concurrence.exo2;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class FastestWithAllItems {
    private final int timeoutMilliPerRequest;
    private final int threadsPerSite;

    public FastestWithAllItems(int timeoutMilliPerRequest, int threadsPerSite) {
        this.timeoutMilliPerRequest = timeoutMilliPerRequest;
        this.threadsPerSite = threadsPerSite;
    }

    public List<Answer> retrieve(List<String> items) throws InterruptedException {
        Objects.requireNonNull(items);
        var sites = Request.getAllSites();
        // Queues
        ArrayBlockingQueue<String>[] itemsForSites = new ArrayBlockingQueue[sites.size()];
        for (var i = 0 ; i < sites.size() ; i++) {
            itemsForSites[i] = new ArrayBlockingQueue<String>(items.size());
            for (var item : items) {
                itemsForSites[i].put(item);
            }
        }
        ArrayBlockingQueue<Answer>[] answersForSite = new ArrayBlockingQueue[sites.size()];
        for (var i = 0 ; i < sites.size() ; i++) {
            answersForSite[i] = new ArrayBlockingQueue<Answer>(items.size());
        }
        var firstSiteFlag = new SynchronousQueue<Integer>();
        // Threads
        var threads = new ArrayList<Thread>();
        for (var i = 0 ; i < sites.size() ; i++) {
            var index = i;
            for (var j = 0 ; j < threadsPerSite ; j++) {
                threads.add(Thread.ofPlatform().daemon().unstarted(() -> {
                        for (;;) {
                            try {
                                var item = itemsForSites[index].take();
                                var request = new Request(sites.get(index), item);
                                var answer = request.request(timeoutMilliPerRequest);
                                if (answer.isPresent()) {
                                    answersForSite[index].put(answer.get());
                                    if (answersForSite[index].size() == items.size()) {
                                        firstSiteFlag.put(index);
                                    }
                                }
                            } catch (InterruptedException e) {
                                return;
                            } catch (SocketTimeoutException e) {}
                        }
                }));
            }
        }
        threads.forEach(Thread::start);
        var firstSite = firstSiteFlag.take();
        return answersForSite[firstSite].stream().toList();
    }

    public static void main(String[] args) {
        try {
            var aggregator = new FastestWithAllItems(2_000, 2);
            var answer = aggregator.retrieve(List.of("tortank", "pikachu", "evoli", "miaouss", "salameche"));
            System.out.println(answer);
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }
}