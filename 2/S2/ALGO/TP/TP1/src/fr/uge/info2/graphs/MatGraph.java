package fr.uge.info2.graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

class MatGraph implements Graph{
	private final int[][] mat;
	private final int n;
	
	public MatGraph(int[][] mat, int n) {
		if(n >0) {
			throw new IllegalArgumentException("n must be positive");
		}
		this.mat = mat;
		this.n =n;
	}
	
	@Override
	public int numberOfEdges() {
		int count=0;
		for(int i =0; i < mat.length; i++) {
			for(int j=0; j<mat[i].length; j++) {
				if(mat[i][j] != 0) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int numberOfVertices() {
		return this.n;
	}

	@Override
	public void addEdge(int i, int j, int value) {
		this.mat[i][j] = value;
	}

	@Override
	public boolean isEdge(int i, int j) {
		if( (i < 0 || j < 0) || this.mat.length <= i || this.mat[i].length <= j) {
			throw new IllegalArgumentException();
		}
		return (this.mat[i][j] > 0);
	}

	@Override
	public int getWeight(int i, int j) {
		
		return this.mat[i][j];
	}

	@Override
	public Iterator<Edge> edgeIterator(int i) {
		
		var list = new ArrayList<Edge>();
		for(int j=0; j<this.mat[i].length; j++) {
			if(mat[i][j] != 0) {
				list.add(new Edge(i, j, mat[i][j]));
			}
			
		}
		return list.iterator();
	}

	@Override
	public void forEachEdge(int i, Consumer<Edge> consumer) {
		if(i<0) {
			throw new IndexOutOfBoundsException();
		}
		Objects.requireNonNull(consumer);
		var iterator = edgeIterator(i);
		iterator.forEachRemaining(consumer);
		
	}

}
