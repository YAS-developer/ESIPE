package secretsanta;


import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Objects;

class SecretSanta{
	private final int nbParticipants;
	private final List<Integer> order;
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition1 = lock.newCondition();
	private final String[] hat;
	private int count;
	
	public SecretSanta(int nbParticipants, List<Integer> order ) {
		Objects.requireNonNull(order);
		if(nbParticipants <= 0) {
			throw new IllegalArgumentException();
		}
		this.nbParticipants = nbParticipants;
		this.hat =  new String[nbParticipants];
		this.order = order;
		lock.lock();
		try {
			this.count=0;
		}
		finally {
			lock.unlock();
		}
	}
	
	
	public String submit(String value) throws InterruptedException{
		lock.lock();
		try {
			var currentIndex = count;
			this.hat[count] = value;
			count++;
			
			if(count > nbParticipants) {
				throw new IllegalStateException();
			}
			
			if(count == nbParticipants) {
				condition1.signalAll();
			}
			condition1.await();
			var index = order.get(currentIndex);
			return this.hat[index];
		}
		finally {
			lock.unlock();
		}
	}
	
	
	
	
	public static void main(String[] args) {
        var secretSanta = new SecretSanta(5, List.of(0, 1, 3, 4, 2));
        int[] waitingTimes = {500, 1000, 1500, 2000, 2500};
        for (var id = 0; id < waitingTimes.length; id++) {
            var waitingTime = waitingTimes[id];
            Thread.ofPlatform().name("Thread " + id).start(() -> {
                try {
                    Thread.sleep(waitingTime);
                    var currentThreadName = Thread.currentThread().getName();
                    System.out.println(currentThreadName + " received " + secretSanta.submit(currentThreadName));
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            });
        }
		
    } 
}