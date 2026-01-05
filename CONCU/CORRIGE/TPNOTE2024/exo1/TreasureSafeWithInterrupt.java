package fr.uge.concurrence.exo1;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TreasureSafeWithInterrupt {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition sumCondition = lock.newCondition();
    private final Condition emptyCondition = lock.newCondition();
    private final int maxValue;
    private final ArrayList<Integer> values = new ArrayList<>();
    private int incomingValue;
    private final Set<Thread> prioritizedThreads = new HashSet<>();
    public final Condition priorityCondition = lock.newCondition();

    public TreasureSafeWithInterrupt(int maxValue) {
        this.maxValue = maxValue;
    }

    public void putTreasure(int value) throws InterruptedException {
        lock.lock();
        try {
            while (prioritizedThreads.size() != 0) {
                priorityCondition.await();
            }
            incomingValue += value;
            while(values.stream().mapToInt(a -> a).sum() + value > maxValue) {
                sumCondition.await();
            }
            values.add(value);
            incomingValue -= value;
            emptyCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int takeTreasure() throws InterruptedException {
        lock.lock();
        try {
            while(values.isEmpty()) {
                emptyCondition.await();
            }
            var returnValue = values.removeLast();
            sumCondition.signalAll();
            return returnValue;
        } finally {
            lock.unlock();
        }
    }

    public void putManyTreasures(List<Integer> treasureList) {
        Objects.requireNonNull(treasureList);
        lock.lock();
        try {
            var wasInterrupted = false;
            incomingValue += treasureList.stream().mapToInt(a -> a).sum();
            for (var value : treasureList) {
                try {
                    while(prioritizedThreads.size() > 0 && !prioritizedThreads.contains(Thread.currentThread())) {
                        priorityCondition.await();
                    }
                    while(values.stream().mapToInt(a -> a).sum() + value > maxValue) {
                        sumCondition.await();
                    }
                    values.add(value);
                    incomingValue -= value;
                    emptyCondition.signalAll();
                } catch (InterruptedException e) {
                    wasInterrupted = true;
                    prioritizedThreads.add(Thread.currentThread());
                }
            }
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
        } finally {
            prioritizedThreads.remove(Thread.currentThread());
            priorityCondition.signalAll();
            lock.unlock();
        }
    }

    public int incomingValue() {
        lock.lock();
        try {
            return incomingValue;
        } finally {
            lock.unlock();
        }
    }
}
