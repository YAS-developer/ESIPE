package fr.uge.conc.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class CheapestSequential {

    private final String item;
    private final int timeoutMilliPerRequest;

    public CheapestSequential(String item, int timeoutMilliPerRequest) {
    		Objects.requireNonNull(item);
        this.item = item;
        this.timeoutMilliPerRequest = timeoutMilliPerRequest;
    }

    /**
     * @return the cheapest price for item if it is sold
     */
    public Optional<Answer> retrieve() throws InterruptedException {
    	var list = new ArrayList<Answer>();
    	var sites = Request.getAllSites();
    	for (var site : sites) {
    		var request = new Request(site, item);
    		var optionalAnswer = request.request(timeoutMilliPerRequest);
//    		if (optionalAnswer.isPresent()) {
//    			list.add(optionalAnswer.orElseThrow());
//    		}
    		optionalAnswer.ifPresent(list::add);
    	}
    	
    	return list.stream()
    						 .min(Comparator.comparingInt(Answer::price));
    }

    public static void main(String[] args) throws InterruptedException {
        var agregator = new CheapestSequential("pikachu", 2_000);
        var answer = agregator.retrieve();
        System.out.println(answer); // Optional[pikachu@darty.fr : 214]
    }
}