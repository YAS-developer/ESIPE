package fr.uge.conc.ex3;


public class ReusableExchanger<T> {
    private enum State { EMPTY, FIRST_WAITING, SECOND_READY }
    
    private volatile State state = State.EMPTY;
    private volatile T firstValue;
    private volatile T secondValue;
    private final Object lock = new Object();

    public T exchange(T value) throws InterruptedException {
        synchronized (lock) {
            switch (state) {
                case EMPTY:
                    firstValue = value;
                    state = State.FIRST_WAITING;
                    while (state != State.SECOND_READY) {
                        lock.wait();
                    }
                    T result = secondValue;
                    secondValue = null;
                    state = State.EMPTY;
                    lock.notify();  // Notify any waiting thread that the exchanger is empty
                    return result;
                
                case FIRST_WAITING:
                    secondValue = value;
                    state = State.SECOND_READY;
                    lock.notify();  // Notify the first thread that the exchange is ready
                    while (state != State.EMPTY) {
                        lock.wait();
                    }
                    return firstValue;
                
                case SECOND_READY:
                    throw new IllegalStateException("Exchanger in an invalid state");
                
                default:
                    throw new AssertionError("Unreachable");
            }
        }
    }
}
