package fr.uge.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

final class MatrixGraph<E> implements Graph<E>{
    private E[] graph;
    private int nodeCount;
    private E defaultValue;

    MatrixGraph(E[] graph, int nodeCount){
        this.graph = graph;
        this.nodeCount = nodeCount;
        super();
    }
    MatrixGraph(E[] graph, int nodeCount, E defaultValue){
        this.graph = graph;
        this.nodeCount = nodeCount;
        this.defaultValue = defaultValue;
        super();
    }
    @Override
    public int nodeCount() {
        return nodeCount;
    }
    @Override
    public E getWeight(int src, int dst){
        Objects.checkIndex(src, nodeCount);
        Objects.checkIndex(dst, nodeCount);
        var node = graph[src * nodeCount + dst];
        return node == null ? defaultValue : node;
    }
    @Override
    public void addEdge(int src, int dst, E weight) {
        Objects.checkIndex(src, nodeCount);
        Objects.checkIndex(dst, nodeCount);
        Objects.requireNonNull(weight);
        if(weight.equals(defaultValue)){
            throw new IllegalArgumentException("same as defaultValue");
        }
        graph[src * nodeCount + dst] = weight;
    }
    @Override
    public Iterable<Edge<E>> edges(int src) {
        Objects.checkIndex(src, nodeCount);
        return () -> new Iterator<>() {
            private int dst;
            @Override
            public boolean hasNext() {
                return nextEdge() != null;
            }
            private Edge<E> nextEdge(){
                var i = dst;
                while(i < nodeCount){
                    var weight = graph[src * nodeCount + i];
                    if(weight != null && !weight.equals(defaultValue)){
                        return new Edge<>(src, i, graph[src * nodeCount + i]);
                    }
                    i++;
                }
                return null;
            }
            @Override
            public Edge<E> next() {
                var nextEdge = nextEdge();
                if (nextEdge == null) {
                    throw new NoSuchElementException("no element");
                }
                dst = nextEdge.dst() + 1;
                return nextEdge;
            }
            @Override
            public void remove(){
                if (dst == 0 || graph[src * nodeCount + dst - 1] == null) {
                    throw new IllegalStateException("Can't remove an element before calling next() or after removing.");
                }
                graph[src * nodeCount + dst - 1] = null;
            }
        };
    }
    @Override
    public Stream<Edge<E>> edgeStream(int src) {
        Objects.checkIndex(src, nodeCount);
        return StreamSupport.stream(new Spliterator<>() {
            @Override
            public boolean tryAdvance(Consumer<? super Edge<E>> action) {
                return false;
            }
            @Override
            public Spliterator<Edge<E>> trySplit() {
                return null;
            }
            @Override
            public long estimateSize() {
                return nodeCount;
            }
            @Override
            public int characteristics() {
                return SIZED | ORDERED;
            }
        }, false);
    }
}