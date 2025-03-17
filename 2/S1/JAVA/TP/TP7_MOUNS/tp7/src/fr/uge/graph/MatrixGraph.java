package fr.uge.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

final class MatrixGraph<T> implements Graph<T> {
	private final T[] array;
	private final int nodeCount;

	@SuppressWarnings("unchecked")
	public MatrixGraph(int nodes) {
		if (nodes < 0) {
			throw new IllegalArgumentException();
		}
		this.nodeCount = nodes;
		this.array = (T[]) new Object[nodes * nodes];
	}

	@Override
	public int nodeCount() {
		return nodeCount;
	}

	public int index(int src, int dst) {
		return nodeCount * src + dst;
	}

	@Override
	public void addEdge(int src, int dst, T weight) {
		Objects.requireNonNull(weight);
		Objects.checkIndex(src, nodeCount);
		Objects.checkIndex(dst, nodeCount);
		array[index(src, dst)] = weight;
	}

	@Override
	public Optional<T> getWeight(int src, int dst) {
		Objects.checkIndex(src, nodeCount);
		Objects.checkIndex(dst, nodeCount);
		return Optional.ofNullable(array[index(src, dst)]);
	}

	@Override
	public Iterator<Integer> neighborIterator(int src) {
	    return new Iterator<>() {
	        private int current = 0;
	        private Integer nextNode = findNext();
	        private boolean canRemove = false;
	        private int lastReturned = -1;

	        private Integer findNext() {
	            while (current < nodeCount) {
	                if (array[index(src, current)] != null) {
	                    return current++;
	                }
	                current++;
	            }
	            return null;
	        }

	        @Override
	        public boolean hasNext() {
	            return nextNode != null;
	        }

	        @Override
	        public Integer next() {
	            if (!hasNext()) {
	                throw new NoSuchElementException();
	            }
	            lastReturned = nextNode;
	            var result = nextNode;
	            nextNode = findNext();
	            canRemove = true;
	            return result;
	        }

	        @Override
	        public void remove() {
	            if (!canRemove) {
	                throw new IllegalStateException("Cannot remove before calling next()");
	            }
	            array[index(src, lastReturned)] = null; // Supprime l’arc dans la matrice
	            canRemove = false;
	        }
	    };
	}

	


}
