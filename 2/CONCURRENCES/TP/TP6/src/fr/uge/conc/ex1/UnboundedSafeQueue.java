package fr.uge.conc.ex1;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class UnboundedSafeQueue<V> {
	private final ArrayDeque<V> list = new ArrayDeque<>();
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();

	public void add(V value) {
		Objects.requireNonNull(value);
		lock.lock();
		try {
			list.add(value);
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	public V take() throws InterruptedException {
		lock.lock();
		try {
			while (list.isEmpty()) {
				condition.await();
			}
		} finally {
			lock.unlock();
		}
		return list.remove();
	}

	public static void main(String[] args) {
		int nbThreads = 3;
		var file = new UnboundedSafeQueue<String>();
		IntStream.range(0, nbThreads).forEach(i -> {
			Thread.ofPlatform().start(() -> {
				while(true) {
					try {
						Thread.sleep(2_000);
						file.add(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						throw new AssertionError();
					}
				}
			});
		});

		for (;;) {
			try {
				System.out.println(file.take());
			} catch (InterruptedException e) {
				throw new AssertionError();
			}
		}

	}
}
