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
		this.timeoutMilliPerRequest = timeoutMilliPerRequest;
		this.threadsPerSite = threadsPerSite;
	
		
	}
	public List<Answer> retrieve(List<String> items) throws InterruptedException {
		
		Objects.requireNonNull(items);
		
		var indexThread = 0;
		var threads = new Thread[threadsPerSite * Request.getAllSites().size()];
		
		var answerQueue = new ArrayBlockingQueue<Answer>(100);
		var itemQueue = new ArrayBlockingQueue<String>(items.size());
		var answerPerSite = new HashMap<String, List<Answer>>();
	
		try {
		
			for(var site: Request.getAllSites()) {
			
				for(var item: items) {
					itemQueue.put(item);
				} 
				
				for (var _: threads) {
					threads[indexThread++] = Thread.ofPlatform().start(() -> {
				        for(;;) {
				            try {
				               
				              
				            	var request = new Request(site, itemQueue.take());
								var answer =  request.request(timeoutMilliPerRequest);
								if (answer.isPresent()) {
									answerQueue.put(answer.orElseThrow());
								} else {
								    System.out.println("The price could not be retrieved from the site");
								}
							} 
				            catch(SocketTimeoutException e) {
				            	
				            }
				            catch (InterruptedException e) {
								return;
							}     
				        }
				    });
				}
				
				for(;;) {
					var answer = answerQueue.take();
					answerPerSite.computeIfAbsent(site, _ -> new ArrayList<>()).add(answer);
					if(answerPerSite.get(answer.site()).size() == items.size()) {{
						return answerPerSite.get(answer.site());
					}
				}
			}
			}
		}
		finally {
			for(var thread: threads) {
				thread.interrupt();
			}
		}
		
		return List.of();
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