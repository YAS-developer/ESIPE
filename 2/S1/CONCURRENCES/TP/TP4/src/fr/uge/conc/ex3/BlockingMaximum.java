package fr.uge.conc.ex3;

public class BlockingMaximum {
    public static final int MAX_VALUE = 10_000;
    private static final int THRESHOLD = 8_000;
    private int max = Integer.MIN_VALUE;
    private boolean thresholdReached = false;
    private final Object lock = new Object();

    public void propose(int value) {
        synchronized (lock) {
            if (value > max) {
                max = value;
                if (max > THRESHOLD && !thresholdReached) {
                    thresholdReached = true;
                    lock.notifyAll();
                }
            }
        }
    }

    public int waitAndGetMax() throws InterruptedException {
        synchronized (lock) {
            while (!thresholdReached) {
                lock.wait();
            }
            return max;
        }
    }

    public int getMax() {
        synchronized (lock) {
            return max;
        }
    }
}