package fr.uge.info2.algoadv;

import java.util.Objects;

public class Edge {
	private final int start;
	private final int end;
	private final int value;
	
	public Edge(int start, int end, int value) {
		this.start = start;
		this.end = end;
		this.value = value;
	}
	
	public Edge(int start, int end) { 
		this(start, end, 1); 
	}
	
	public int getValue() { 
		return value; 
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end; 
	}
	
	@Override
	public String toString() {
		return start + " -- " + end + " ( " + value + " )";
	}
	
	public String toStringGraphviz() {
		return start + " -> " + end + " [label=\"" + value + "\"];\n";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Edge) {
			var obje = (Edge) obj;
			return start == obje.start && end == obje.end && value == obje.value; 
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() { 
		return Objects.hash(start, end, value);
	}
}
