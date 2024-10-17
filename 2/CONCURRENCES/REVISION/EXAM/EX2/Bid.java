import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class Bid {
    private final AtomicIntegerArray bids;
    private final ReentrantLock lock;
    private final Condition[] conditions;
    private int smallestBidder;
    private boolean allBidsPlaced;

    public Bid(int numThreads) {
        this.bids = new AtomicIntegerArray(numThreads);
        this.lock = new ReentrantLock();
        this.conditions = new Condition[numThreads];
        for (int i = 0; i < numThreads; i++) {
            conditions[i] = lock.newCondition();
        }
        this.smallestBidder = -1;
        this.allBidsPlaced = false;
    }

    public boolean placeBid(int threadId, int value) {
        if (bids.get(threadId) == 0) {
            bids.set(threadId, value);
            lock.lock();
            try {
                if (smallestBidder == -1 || value < bids.get(smallestBidder)) {
                    smallestBidder = threadId;
                }
                checkAllBidsPlaced();
            } finally {
                lock.unlock();
            }
            return true;
        }
        return false;
    }

    private void checkAllBidsPlaced() {
        for (int i = 0; i < bids.length(); i++) {
            if (bids.get(i) == 0) return;
        }
        allBidsPlaced = true;
        conditions[smallestBidder].signal();
    }

    public int waitForTurn(int threadId) throws InterruptedException {
        lock.lock();
        try {
            while (!allBidsPlaced || threadId != smallestBidder) {
                conditions[threadId].await();
            }
            return calculateAverage();
        } finally {
            lock.unlock();
        }
    }

    private int calculateAverage() {
        int sum = 0;
        for (int i = 0; i < bids.length(); i++) {
            sum += bids.get(i);
        }
        return sum / bids.length();
    }

    public void updateBid(int threadId, int newValue) {
        bids.set(threadId, newValue);
        lock.lock();
        try {
            updateSmallestBidder();
            conditions[smallestBidder].signal();
        } finally {
            lock.unlock();
        }
    }

    private void updateSmallestBidder() {
        int newSmallest = 0;
        for (int i = 1; i < bids.length(); i++) {
            if (bids.get(i) < bids.get(newSmallest)) {
                newSmallest = i;
            }
        }
        smallestBidder = newSmallest;
    }

    public void signalRejection(int threadId) {
        lock.lock();
        try {
            conditions[threadId].signal();
        } finally {
            lock.unlock();
        }
    }
}