package fr.uge.info2.algoadv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class AdjGraph implements Graph {
	private final ArrayList<LinkedList<Edge>> adj;
	private final int n; // number of vertices
	private int edgeCount = 0;

	public AdjGraph(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Nombre de sommets négatif");
		this.n = n;
		this.adj = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			adj.add(new LinkedList<>());
		}
	}

	@Override
	public int numberOfEdges() {
		return edgeCount;
	}

	@Override
	public int numberOfVertices() {
		return n;
	}

	@Override
	public void addEdge(int i, int j, int value) {
		checkIndices(i, j);

		if (isEdge(i, j)) {
			throw new IllegalArgumentException("Edge already exist");
		}
		
		adj.get(i).add(new Edge(i, j, value));
		edgeCount++;
	}

	@Override
	public boolean isEdge(int i, int j) {
		checkIndices(i, j);
		for (var e : adj.get(i)) {
			if (e.getEnd() == j) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getWeight(int i, int j) {
		checkIndices(i, j);
		for (Edge e : adj.get(i)) {
			if (e.getEnd() == j) {
				return e.getValue();
			}
		}
		return 0;
	}

	@Override
	public Iterator<Edge> edgeIterator(int i) {
		checkVertex(i);
		return adj.get(i).iterator();
	}

	@Override
	public void forEachEdge(int i, Consumer<Edge> consumer) {
		checkVertex(i);
		for (var e : adj.get(i)) {
			consumer.accept(e);
		}
	}

	@Override
	public String toGraphviz() {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		// Affichage de tous les sommets
		for (int i = 0; i < n; i++) {
			sb.append("  ").append(i).append(";\n");
		}
		// Parcours de chaque liste d'adjacence
		for (int i = 0; i < n; i++) {
			for (Edge e : adj.get(i)) {
				sb.append("  ").append(e.getStart()).append(" -> ").append(e.getEnd()).append(" [ label=\"")
						.append(e.getValue()).append("\" ];\n");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	private void checkIndices(int i, int j) {
		if (i < 0 || i >= n || j < 0 || j >= n) {
			throw new IllegalArgumentException("Indices hors limites.");
		}
	}

	private void checkVertex(int i) {
		if (i < 0 || i >= n)
			throw new IllegalArgumentException("Sommet " + i + " hors limites.");
	}

	@Override
	public boolean removeEdge(int i, int j) {
		// TODO Auto-generated method stub
		return false;
	}

}
