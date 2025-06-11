package fr.uge.info2.algoadv;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class AdjGraphTest {

	@Test
	void testNumberOfEdges() {
		var g = new AdjGraph(10);
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				g.addEdge(i, j, 1);
				try {
					g.addEdge(i, j, 1);
				} catch (Exception e) {
					continue;
				}
			}
		}
		if (g.numberOfEdges() != 100) {
			fail("Not yet implemented");
		}
	}

	@Test
	void testAddEdge() {
		var g = new AdjGraph(10);
		// test value 0
		try {
			g.addEdge(0, 0, 0);
			fail("Value 0 error not raised");
		} catch (Exception e) {
			// we should get to here, and should not raise error
			g.addEdge(0, 0, 1);
		}
		if (!g.isEdge(0, 0) || g.numberOfEdges() != 1) {
			fail("Loop not successfully added");
		}
		// test general values
		g.addEdge(0, 1, 1);
		if (!g.isEdge(0, 1) || g.numberOfEdges() != 2) {
			fail("Plain edge not successfully added");
		}
		// test double adding
		try {
			g.addEdge(0, 1, -1);
			fail("Double edge error not raised");
		} catch (Exception e) {
			// we should come here
			if(g.getWeight(0, 1) != 1) {
				fail("Not getting correct weight");
			}
		}
	}

	@Test
	void testIsEdge() {
		var g = new AdjGraph(10);
		g.addEdge(2, 3, -10);
		if (!g.isEdge(2, 3)) {
			fail("Did not detect existing edge");
		}
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(i == 2 && j == 3) continue;
				if(g.isEdge(i, j)) {
					fail("Detecting phantom edges");
				}
			}
		}
	}

	@Test
	void testGetWeight() {
		var g = new AdjGraph(10);
		g.addEdge(5, 3, 1337);
		if (g.getWeight(5, 3) != 1337) {
			fail("Not getting proper weight");
		}
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(i == 5 && j == 3) continue;
				if(g.getWeight(i, j) != 0) {
					fail("Detecting phantom edges");
				}
			}
		}
	}
	
	@Test
	void testForEachEdge() {
		var g = new AdjGraph(10);
		g.addEdge(2, 9, 1);
		g.addEdge(3, 8, 1);
		g.addEdge(2, 8, 10);
		g.forEachEdge(0, e -> fail("Detecting phantom edges"));
		var accu = new ArrayList<Edge>(4);
		g.forEachEdge(2, e -> accu.add(e));
		if (accu.size() != 2) {
			fail("Not getting all edges");
		}
		g.forEachEdge(3, e -> accu.add(e));
		if (accu.size() != 3) {
			fail("Not getting all edges, part 2");
		}
	}

	@Test
	void testRemoveEdge() {
		var g = new AdjGraph(10);
		try {
			if(g.removeEdge(0, 0)) {
				// should not raise any error
				fail("Wrong return value, should be false");
			}
		} catch (Exception e) {
			fail("Should not raise error");
		}
		g.addEdge(2, 4, 6);
		g.addEdge(3, 1, -9);
		if (!g.removeEdge(2, 4)) {
			fail("Wrong return value, should be true");
		}
		if (g.isEdge(2, 4)) {
			fail("Edge not really removed");
		}
	}

}
