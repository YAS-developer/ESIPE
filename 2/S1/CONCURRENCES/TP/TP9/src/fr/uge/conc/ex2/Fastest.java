package fr.uge.conc.ex2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

import fr.uge.conc.api.Answer;
import fr.uge.conc.api.Request;

public class Fastest {

	private final String item;
	private final int timeoutMilliPerRequest;

	public Fastest(String item, int timeoutMilliPerRequest) {
		Objects.requireNonNull(item);
		this.item = item;
		this.timeoutMilliPerRequest = timeoutMilliPerRequest;
	}

	/**
	 * @return the cheapest price for item if it is sold
	 */
	public Optional<Answer> retrieve() throws InterruptedException {

		var queue = new SynchronousQueue<Optional<Answer>>(); // On recevra toutes les réponses même si elles sont pas bonne
		var threadList = new ArrayList<Thread>();
		var sites = Request.getAllSites();

		for (var site : sites) {
			Thread.ofPlatform().name("Thread-" + site).start(() -> {
				threadList.add(Thread.currentThread());
				try {
					var request = new Request(site, item);
					queue.put(request.request(timeoutMilliPerRequest));
				} catch (InterruptedException e) {
					return;
				}
			});
		}

		try {
			for (var i = 0; i < threadList.size() ; i++) {
				var fastValue = queue.take();
				if (fastValue.isPresent()) {
					return fastValue;
				}
			}
			return Optional.empty();

		} finally { // Dans n'importe quel cas on interrompt à la fin 
			threadList.forEach(Thread::interrupt);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		//		var agregator = new Fastest("tortank", 2_000);
		var agregator = new Fastest("pokeball", 2_000);
		var answer = agregator.retrieve();
		System.out.println(answer); // Optional[tortank@... : ...]


	}
}