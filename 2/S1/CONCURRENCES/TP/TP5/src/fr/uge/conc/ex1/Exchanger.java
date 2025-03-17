package fr.uge.conc.ex1;

public class Exchanger<T> {
    private volatile T firstValue;
    private volatile T secondValue;
    private volatile boolean firstArrived = false;
    private final Object lock = new Object();

    public T exchange(T value) throws InterruptedException {
        synchronized (lock) {
            if (!firstArrived) {
                // Premier appel
                firstValue = value;
                firstArrived = true;
                while (secondValue == null) {
                    lock.wait();
                }
                T result = secondValue;
                secondValue = null;
                firstArrived = false;
                return result;
            } else {
                // Deuxième appel
                secondValue = value;
                lock.notify();
                return firstValue;
            }
        }
    }
}