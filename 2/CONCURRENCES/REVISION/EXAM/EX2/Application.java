

[;public class Application {
    public static void main(String[] args) {
        final Bid bid = new Bid(5);

        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                int currentBid = threadId + 1;
                while (true) {
                    try {
                        Thread.sleep(1000);
                        if (bid.placeBid(threadId, currentBid)) {
                            System.out.println("Thread " + (threadId + 1) + " proposes " + currentBid);
                            int average = bid.waitForTurn(threadId);
                            System.out.println("Thread " + (threadId + 1) + " was unblocked because its proposed value " + currentBid + " is now the smallest");
                            currentBid = (int) Math.ceil(average);
                        } else {
                            System.out.println("Thread " + (threadId + 1) + " proposed value " + currentBid + " was rejected");
                            currentBid++;
                            bid.signalRejection(threadId);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}