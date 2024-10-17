import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicExchanger<T> {
    private final int nbParticipants;
    private final ReentrantLock lock;
    private final Condition condition;
    private final T[] values;
    private int count;
    private int exchangeId;

    @SuppressWarnings("unchecked")
    public CyclicExchanger(int nbParticipants) {
        this.nbParticipants = nbParticipants;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.values = (T[]) new Object[nbParticipants];
        this.count = 0;
        this.exchangeId = 0;
    }

    public T exchange(T value) throws InterruptedException {
        lock.lock();
        try {
            int currentExchangeId = exchangeId;
            int myPosition = count;
            
            if (count >= nbParticipants) {
                throw new IllegalStateException("Le nombre maximum de participants a été atteint.");
            }
            
            values[myPosition] = value;
            count++;
            
            if (count == nbParticipants) {
                count = 0;
                exchangeId++;
                condition.signalAll();
            } else {
                while (currentExchangeId == exchangeId) {
                    condition.await();
                }
            }
            
            return values[(myPosition + 1) % nbParticipants];
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final int NB_THREADS = 5;
        CyclicExchanger<Integer> exchanger = new CyclicExchanger<>(NB_THREADS);

        for (int i = 0; i < NB_THREADS; i++) {
            final var threadId = i;
            new Thread(() -> {
                try {
                    // Attendre i secondes
                    Thread.sleep(threadId * 1000);

                    // Appeler exchange avec la valeur i
                    int result = exchanger.exchange(threadId);

                    // Afficher le résultat
                    System.out.println("Thread " + threadId + " a échangé " + threadId + " et reçu " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}