package fr.uge.concurrence.exo2;

import java.net.SocketTimeoutException;
import java.nio.channels.ClosedChannelException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Request {

	private final String site;
	private final String item;
	private final Object lock = new Object();
	private boolean calledOnce;

	private final static String[] ARRAY_ALL_SITES = { "fmac.fr", "tardy.fr", "patissier.fr", "bdiscount.fr",
			"tombeducamion.fr", "omazen.uk", "leboncorner.fr", "princedesvoleurs.com", "ledoute.fr",
			"les3alpes.fr", "shady.com"};
	private final static Set<String> SET_ALL_SITES = Set.of(ARRAY_ALL_SITES);
	private final static List<String> ALL_SITES = List.of(ARRAY_ALL_SITES);

	public static List<String> getAllSites() {
		return ALL_SITES;
	}

	public Request(String site, String item) {
		if (!SET_ALL_SITES.contains(site)) {
			throw new IllegalStateException("invalid site " + site);
		}
		this.site = site;
		this.item = item;
	}

	@Override
	public String toString() {
		return item + "@" + site;
	}

	/**
	 * Performs the request the price for the item on the site waiting at most
	 * timeoutMilli milliseconds. The returned Answered is not guaranteed to be
	 * successful.
	 *
	 * This method can only be called once. All further calls will throw an
	 * IllegalStateException
	 *
	 *
	 * @param timeoutMilli
	 * @throws InterruptedException
	 * @throws SocketTimeoutException if no answer is retrieved before timeout milliseconds
	 */
	public Optional<Answer> request(int timeoutMilli) throws InterruptedException, SocketTimeoutException {
		synchronized (lock) {
			if (calledOnce) {
				throw new IllegalStateException("already called once");
			}
			calledOnce = true;
		}
		System.out.println("DEBUG : starting request for " + item + " on " + site);

		long hash1 = (site + "|" + item).hashCode() & 0x7FFF_FFFF;
		long hash2 = (item + "|" + site).hashCode() & 0x7FFF_FFFF;
		int price = (int) (hash2 % 1_000) + 1;
		
		if (site.equals(ALL_SITES.getLast())) {
			Thread.sleep((hash1 % 1_000) * 3);
			System.out.println("DEBUG : " + item + " costs " + price + " on " + site);
			return Optional.of(new Answer(site, item, price));
		}
		if ((hash1 % 1_000 < 100) || ((hash1 % 1_000) * 2 > timeoutMilli)) { // simulating timeout
			Thread.sleep(timeoutMilli);
			System.out.println("DEBUG : Request " + toString() + " has timeout");
			throw new SocketTimeoutException(site);
		}
		Thread.sleep((hash1 % 1_000) * 2);
		if ((hash1 % 1_000 < 250)) {
			System.out.println("DEBUG : " + item + " is not available on " + site);
			return Optional.empty();
		}
		
		System.out.println("DEBUG : " + item + " costs " + price + " on " + site);
		return Optional.of(new Answer(site, item, price));
	}
}