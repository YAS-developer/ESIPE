package fr.uge.conc.ex4;

import java.util.HashMap;
import java.util.Map;

public class Vote {
    private final int expectedVotes;
    private final Map<String, Integer> votes;
    private int voteCount;
    private String winner;
    private final Object lock = new Object();

    public Vote(int expectedVotes) {
        if (expectedVotes <= 0) {
            throw new IllegalArgumentException("Expected votes must be positive");
        }
        this.expectedVotes = expectedVotes;
        this.votes = new HashMap<>();
        this.voteCount = 0;
    }

    public String vote(String candidate) throws InterruptedException {
        synchronized (lock) {
            if (winner != null) {
                return winner;
            }

            votes.merge(candidate, 1, Integer::sum);
            voteCount++;

            if (voteCount == expectedVotes) {
                winner = computeWinner();
                lock.notifyAll();
            } else {
                while (winner == null) {
                    lock.wait();
                }
            }

            return winner;
        }
    }

    private String computeWinner() {
        var score = -1;
        String winner = null;
        for (var e : votes.entrySet()) {
            var key = e.getKey();
            var value = e.getValue();
            if (value > score || (value == score && key.compareTo(winner) < 0)) {
                winner = key;
                score = value;
            }
        }
        return winner;
    }
}