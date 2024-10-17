public class Application {
    public static void main(String[] args) {
        final Bid bid = new Bid(5);

        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            Thread.ofPlatform().start(() -> {
                int currentBid = threadId + 1;
                while (true) {
                    try {
                        Thread.sleep(1000);
                        boolean success = bid.placeBid(threadId, currentBid);
                        if (success) {
                            System.out.println("Thread " + (threadId + 1) + " proposes " + currentBid);
                            Bid.BidResult result = bid.waitForTurn(threadId);
                            if (result.isSmallest) {
                                System.out.println("Thread " + (threadId + 1) + " was unblocked because its proposed value " + currentBid + " is now the smallest");
                                currentBid = (int) Math.ceil(result.average);
                            } else {
                                System.out.println("Thread " + (threadId + 1) + " proposed value " + currentBid + " was rejected");
                                currentBid++;
                            }
                        } else {
                            System.out.println("Thread " + (threadId + 1) + " proposed value " + currentBid + " was rejected");
                            currentBid++;
                        }
                    } catch (InterruptedException e) {
                        throw new AssertionError(e);
                    }
                }
            });
        }
    }
}