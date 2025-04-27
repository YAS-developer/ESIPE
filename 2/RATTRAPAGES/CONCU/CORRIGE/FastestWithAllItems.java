package fr.uge.concurrence.exo2;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

public class FastestWithAllItems {
	private final int timeoutMilliPerRequest;
	private final int threadsPerSite;

	public FastestWithAllItems(int timeoutMilliPerRequest, int threadsPerSite) {
		if(timeoutMilliPerRequest <= 0 || threadsPerSite <= 0) {
			throw new IllegalArgumentException();
		}
		this.timeoutMilliPerRequest = timeoutMilliPerRequest;
		this.threadsPerSite = threadsPerSite;
	}

	public List<Answer> retrieve(List<String> items) throws InterruptedException {
		Objects.requireNonNull(items);
		var numberOfThread = 0;
		var threads = new Thread[threadsPerSite * Request.getAllSites().size()];
		var answersQueue = new ArrayBlockingQueue<Answer>(100);
		var answersPerSite = new HashMap<String, List<Answer>>();
		try {
			for(var site : Request.getAllSites()) {
				var itemsQueue = new ArrayBlockingQueue<String>(items.size());
				for(var item : items) {
					itemsQueue.put(item);
				}
				for(var i = 0 ; i < threadsPerSite ; i++) {
					threads[numberOfThread++] = Thread.ofPlatform().start(() -> {
						for(;;) {
							try {
								var request = new Request(site, itemsQueue.take());
								var answer = request.request(timeoutMilliPerRequest);
								if(answer.isPresent()) {
									answersQueue.put(answer.orElseThrow());
								}

							 } 
							catch(SocketTimeoutException e){
							
							} 
							catch (InterruptedException e ) {
								return;
							}
						}
					});
				}
			}
			for(;;) {
				var answer = answersQueue.take();
				answersPerSite.computeIfAbsent(answer.site(), k -> new ArrayList<>()).add(answer);
				if(answersPerSite.get(answer.site()).size() == items.size()) {
					return answersPerSite.get(answer.site());
				}
			}
		} finally {
			for(var thread : threads) {
				thread.interrupt();
			}
		}
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