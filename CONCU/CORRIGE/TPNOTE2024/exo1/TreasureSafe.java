package fr.uge.concurrence.exo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TreasureSafe {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition sumCondition = lock.newCondition();
    private final Condition emptyCondition = lock.newCondition();
    private int treasureSum;
    private final int maxValue;
    private final ArrayList<Integer> values = new ArrayList<>();
    private int incomingValue;

    public TreasureSafe(int maxValue) {
        this.maxValue = maxValue;
    }

    public void putTreasure(int value) throws InterruptedException {
        lock.lock();
        try {
            incomingValue += value;
            while(treasureSum + value > maxValue) {
                sumCondition.await();
            }
            values.add(value);
            treasureSum += value;
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
            treasureSum -= returnValue;
            sumCondition.signalAll();
            return returnValue;
        } finally {
            lock.unlock();
        }
    }

    public void putManyTreasures(List<Integer> treasureList) throws InterruptedException {
        Objects.requireNonNull(treasureList);
        lock.lock();
        try {
            incomingValue += treasureList.stream().mapToInt(a -> a).sum();
            for (var value : treasureList) {
                while(values.stream().mapToInt(a -> a).sum() + value > maxValue) {
                    sumCondition.await();
                }
                values.add(value);
                treasureSum += value;
                incomingValue -= value;
                emptyCondition.signalAll();
            }
        } finally {
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
