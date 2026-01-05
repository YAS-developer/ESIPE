package fr.uge.concurrence.exo2;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class FastestWithAllItemsWithInterrupt {
    private final int timeoutMilliPerRequest;
    private final int threadsPerSite;

    public FastestWithAllItemsWithInterrupt(int timeoutMilliPerRequest, int threadsPerSite) {
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
        var threadsForSite = new ArrayList<ArrayList<Thread>>();
        var threadCount = 0;
        for (var i = 0 ; i < sites.size() ; i++) {
            threadsForSite.add(new ArrayList<Thread>());
            var index = i;
            threadCount++;
            for (var j = 0 ; j < threadsPerSite ; j++) {
                threadsForSite.get(index).add(Thread.ofPlatform().name("Thread-" + threadCount).daemon().unstarted(() -> {
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
                                } else {
                                    threadsForSite.get(index).forEach(Thread::interrupt);
                                }
                            } catch (InterruptedException e) {
                                System.out.println(Thread.currentThread().getName() + " interrupted for site " + sites.get(index));
                                Thread.currentThread().interrupt();
                                return;
                            } catch (SocketTimeoutException e) {
                                threadsForSite.get(index).forEach(Thread::interrupt);
                            }
                        }
                }));
            }
        }
        threadsForSite.stream().flatMap(List::stream).forEach(Thread::start);
        var firstSite = firstSiteFlag.take();
        return answersForSite[firstSite].stream().toList();
    }

    public static void main(String[] args) {
        try {
            var aggregator = new FastestWithAllItemsWithInterrupt(2000, 2);
            var answer = aggregator.retrieve(List.of("tortank", "pikachu", "evoli", "miaouss", "salameche"));
            System.out.println(answer);
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }
}