package fr.uge.graph;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fr.uge.graph.Graph.Edge;

/// An oriented graph with values on edges and not on nodes.
public sealed interface Graph<T> permits MatrixGraph {
	/// Returns the number of nodes of this graph.
	/// @return the number of nodes of this graph.
	int nodeCount();

	/// Create a graph implementation based on a matrix.
	///
	/// @param <T> type of the edge weight.
	/// @param nodeCount the number of nodes.
	/// @return a new implementation of Graph.
	static <T> Graph<T> createMatrixGraph(int nodeCount) {
		return new MatrixGraph<>(nodeCount);
	}

	/// Add an edge between two nodes or replace it if an edge already exists.
	///
	/// @param src source node.
	/// @param dst destination node.
	/// @param weight weight of the edge.
	/// @throws NullPointerException if weight is `null`.
	/// @throws IndexOutOfBoundsException if src or dst is not a valid node number.
	void addEdge(int src, int dst, T weight);

	/// Return the weight of an edge.
	///
	/// @param src source ndoe.
	/// @param dst destination nde.
	/// @return the weight of the edge between `src`and `dst` or Optional.empty().
	/// @throws IndexOutOfBoundsException if src or dst is not a valid node number.
	Optional<T> getWeight(int src, int dst);

	/// Adds all the edge values of the graph taken as parameter to the current
	/// graph,
	/// uses the `merger` if there is already a value to merge the value.
	///
	/// @param graph a graph
	/// @param merger the function to call if there are two values to merge.
	/// @throws NullPointerException if either graph or merger is null.
	/// @throws IllegalArgumentException if the graphs do not have the same number
	/// of nodes.
	default void mergeAll(Graph<? extends T> other, BinaryOperator<T> merger) {
		Objects.requireNonNull(other);
		Objects.requireNonNull(merger);
		if (this.nodeCount() != other.nodeCount()) {
			throw new IllegalArgumentException();
		}
		for (var i = 0; i < nodeCount(); i++) {
			var src = i;
			for (var j = 0; j < nodeCount(); j++) {
				var dst = j;
//	            var weight1 = getWeight(i, j);
//	            var weight2 = other.getWeight(i, j); 
//	            if (!weight2.isEmpty()) {
//	            	var newValue = weight1
//	    	                .map(cw -> merger.apply(cw, weight2.orElseThrow()))
//	    	                .orElse(weight2.get());      
//	    	            addEdge(i, j, newValue);
//	            }
				other.getWeight(src, dst).ifPresent(otherWeight -> addEdge(src, dst, getWeight(src, dst)
						.map(currentWeight -> merger.apply(currentWeight, otherWeight)).orElse(otherWeight)));
			}
		}
	}

	/// Returns all the nodes that are connected to the node taken as parameter.
	/// The order of the nodes may be different that the insertion order.
	/// @param src a node.
	/// @return an iterator on all nodes connected to the specified source node.
	/// @throws IndexOutOfBoundsException if src is not a valid node number.
	Iterator<Integer> neighborIterator(int src);

	/// An edge of the graph.
	///
	/// @param src the index of the source node.
	/// @param dst the index of the destination node.
	/// @param weight the weight associated to the edge.
	/// @param <T> the type of the weight
	record Edge<T>(int src, int dst, T weight) {
	}

	/// Call the consumer for each edge associated to the source node.
	///
	/// @param src the source node.
	/// @param function the function called for all edge that have src as source
	/// node.
	/// @throws NullPointerException if consumer is null.
	/// @throws IndexOutOfBoundsException if src is not a valid index for a node.

	default public void forEachEdge(int src, Consumer<Edge<T>> consumer) {
		Objects.requireNonNull(consumer);
		Objects.checkIndex(src, nodeCount());

		for (int i = 0; i < nodeCount(); i++) {
			var dst = i;
            getWeight(src, i).ifPresent(weight -> consumer.accept(new Edge<>(src, dst, weight)));
        }
	}

	/// Returns all the edges of the graph that have a value.
	///
	/// @return all the edges of the graph that have a value in any order.
//	default Stream<Edge<T>> edges() {
//		return IntStream.range(0, nodeCount())
//	            .mapMulti((src, downstream) -> forEachEdge(src, downstream::accept));
//    }

	/// Create a graph implementation based on a node map.
	/// @param nodeCount the number of nodes
	/// @return a new graph implementation
	/// @param <T> type of the edge weight
	// createNodeMapGraph(nodeCount)
}
