package fr.uge.info2.algoadv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class MatGraph implements Graph {
	private final int[][] mat;
	private final int n; // number of vertices
	
	
	public MatGraph(int n) {
        if (n < 0) throw new IllegalArgumentException("Nombre de sommets négatif");
        this.n = n;
        this.mat = new int[n][n];
    }
	
	@Override
	public int numberOfEdges() {
		var count=0;
		for(int i=0;i<mat.length;i++) {
			for(int j = 0 ; j< mat[i].length;j++) {
				if(mat[i][j] != 0) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int numberOfVertices() {
		return n;
	}

	@Override
	public void addEdge(int i, int j, int value) {
		checkIndices(i, j);
		if(mat[i][j] != 0) {
			throw new IllegalArgumentException();
		}
		mat[i][j] = value;
	}

	@Override
	public boolean isEdge(int i, int j) {
		checkIndices(i, j);
		return mat[i][j] !=0;
	}

	@Override
	public int getWeight(int i, int j) {
		if( i< 0 || j< 0 || i > mat.length || j > mat[0].length) {
			throw new IndexOutOfBoundsException();
		}
		return mat[i][j];
	}

	@Override
	public Iterator<Edge> edgeIterator(int i) {
		checkVertex(i);
		var list = new ArrayList<Edge>();
			for(int j = 0 ; j< mat[i].length;j++) {
				if(mat[i][j] != 0) {
					var edge = new Edge(i, j, mat[i][j]);
					list.add(edge);
				}
			}		
		return list.iterator();
	}

	@Override
	public void forEachEdge(int i, Consumer<Edge> consumer) {
		Objects.requireNonNull(consumer);
		checkVertex(i);
		var iterator = edgeIterator(i);
		iterator.forEachRemaining(consumer);
		
	}
	
	@Override
	public String toGraphviz() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("digraph G {\n");
	    // On s'assure d'afficher chaque sommet, même sans arcs sortants
	    for(int i = 0; i < n; i++) {
	        sb.append("  ").append(i).append(";\n");
	    }
	    for (int i = 0; i < mat.length; i++) {
	        for (int j = 0; j < mat[i].length; j++) {
	            if (mat[i][j] != 0) {
	                sb.append("  ").append(i).append(" -> ").append(j)
	                  .append(" [ label=\"").append(mat[i][j]).append("\" ];\n");
	            }
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
