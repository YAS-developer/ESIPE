import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class Bid {
    private final AtomicIntegerArray bids;
    private final ReentrantLock lock;
    private final Condition condition;
    private int smallestBidder;
    private boolean allBidsPlaced;

    public Bid(int numThreads) {
        this.bids = new AtomicIntegerArray(numThreads);
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.smallestBidder = -1;
        this.allBidsPlaced = false;
    }

    public boolean placeBid(int threadId, int value) {
        lock.lock();
        try {
            if (bids.get(threadId) != 0) {
                return false;
            }
            for (int i = 0; i < bids.length(); i++) {
                if (i != threadId && bids.get(i) == value) {
                    return false;
                }
            }
            bids.set(threadId, value);
            if (smallestBidder == -1 || value < bids.get(smallestBidder)) {
                smallestBidder = threadId;
            }
            checkAllBidsPlaced();
            return true;
        } finally {
            lock.unlock();
        }
    }

    private void checkAllBidsPlaced() {
        for (int i = 0; i < bids.length(); i++) {
            if (bids.get(i) == 0) return;
        }
        allBidsPlaced = true;
        condition.signalAll();
    }

    public BidResult waitForTurn(int threadId) throws InterruptedException {
        lock.lock();
        try {
            while (!allBidsPlaced || threadId != smallestBidder) {
                condition.await();
            }
            if (threadId == smallestBidder) {
                return new BidResult(true, calculateAverage());
            } else {
                return new BidResult(false, 0);
            }
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
        lock.lock();
        try {
            bids.set(threadId, newValue);
            updateSmallestBidder();
            condition.signalAll();
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

    public static class BidResult {
        public final boolean isSmallest;
        public final int average;

        public BidResult(boolean isSmallest, int average) {
            this.isSmallest = isSmallest;
            this.average = average;
        }
    }
}
