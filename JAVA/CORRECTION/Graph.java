package fr.uge.graph;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/// An interface representing a weighted graph data structure.
/// This interface defines the core operations for managing nodes and edges
/// in a graph where nodes are predefined and edges have associated weights of type E.
///
/// @param <E> the type of edge weights
public sealed interface Graph<E> permits MatrixGraph {

    /// Returns the total number of nodes in the graph.
    ///
    /// @return the number of nodes in this graph
    int nodeCount();

    /// Retrieves the weight of the edge between two nodes.
    ///
    /// @param src the source node index
    /// @param dst the destination node index
    /// @return the weight of the edge from src to dst, or the default value if no edge exists
    /// @throws IllegalArgumentException if src or dst is negative
    E getWeight(int src, int dst);

    /// Adds an edge with the specified weight between two nodes.
    /// If an edge already exists between the nodes, its weight is updated.
    ///
    /// @param src the source node index
    /// @param dst the destination node index
    /// @param weight the weight to assign to the edge
    /// @throws IllegalArgumentException if src or dst is negative or if the weight is the default value
    void addEdge(int src, int dst, E weight);

    record Edge<E>(int src, int dst, E weight){
        public Edge{
            Objects.requireNonNull(weight);
            if(src < 0){
                throw new IllegalArgumentException("negative source index");
            }
            if(dst < 0){
                throw new IllegalArgumentException("negative destination index");
            }
        }
    }

    /// Returns an iterable of all edges originating from the specified node.
    ///
    /// @param src the source node index
    /// @return an Iterable of Edge objects representing all outgoing edges from the source node
    /// @throws IllegalArgumentException if src is negative
    Iterable<Edge<E>> edges(int src);

    /// Returns a stream of all edges originating from the specified node.
    ///
    /// @param src the source node index
    /// @return a Stream of Edge objects representing all outgoing edges from the source node
    /// @throws IllegalArgumentException if src is negative
    Stream<Edge<E>> edgeStream(int src);

    @SuppressWarnings("unchecked")
    static <E> Graph<E> of(int nodeCount){
        if(nodeCount < 0){
            throw new IllegalArgumentException();
        }
        var graph = (E[]) new Object[nodeCount * nodeCount];
        return new MatrixGraph<>(graph, nodeCount);
    }

    @SuppressWarnings("unchecked")
    static <E> Graph<E> of(int nodeCount, E defaultValue){
        if(nodeCount < 0){
            throw new IllegalArgumentException("negative node");
        }
        var graph = (E[]) new Object[nodeCount * nodeCount];
        Arrays.fill(graph, defaultValue);
        return new MatrixGraph<>(graph, nodeCount, defaultValue);
    }
}