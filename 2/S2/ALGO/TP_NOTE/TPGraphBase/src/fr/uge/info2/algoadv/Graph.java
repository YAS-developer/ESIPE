package fr.uge.info2.algoadv;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Graph {
	// General interface for a data structure representing a graph
	// vertices are represented by integers from 0 to n - 1
	// we assume that the graph is simple, i.e., no multiple edges
	
	// returns the number of edges, which may change
	int numberOfEdges();
	
	// return the number of vertices, which does not change
	int numberOfVertices();
	
	// add an edge, and raise an error when an edge already exists or value == 0
	void addEdge(int i, int j, int value);
	
	// remove an edge, and returns whether the edge is removed
	boolean removeEdge(int i, int j);
	
	// test whether an edge exits
	boolean isEdge(int i, int j);
	
	// obtain the weight of a given edge. If the edge does not exist, it returns 0.
	int getWeight(int i, int j);
	
	// iterate through all edges starting from vertex i
	Iterator<Edge> edgeIterator(int i);
	
	// iterate a function over all edges starting from vertex i
	void forEachEdge(int i, Consumer<Edge> consumer);
	
	// convert the graph to dot format
	String toGraphviz();
}