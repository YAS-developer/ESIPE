/**
 *  Test for GraphAlgo (to be implemented by students). Only contains some basic tests.
 */
package fr.uge.info2.algoadv;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * 
 */
class GraphAlgoTest {
	
	private String getResourcePath(String filename) {
		return "resources/" + filename + ".txt";
	}
	
	private AdjGraph loadGraph(String filename) {
		// the format is:
		// starting with two numbers, the number of vertices and of edges
		// then for each edge, we have a line for start, end and value
		String filepath = getResourcePath(filename);
		try (var scanner = new Scanner(new File(filepath))) {
			// read size information
			int n = scanner.nextInt();
			int edgeCount = scanner.nextInt();
			var graph = new AdjGraph(n);
			// read each edge
			for (int i = 0; i < edgeCount; i++) {
				int start = scanner.nextInt();
				int end = scanner.nextInt();
				int value = scanner.nextInt();
				graph.addEdge(start, end, value);
			}
			return graph;
		} catch (Exception e) {
			fail(e);
			return new AdjGraph(1);
		}
	}
	
	/**
	 *  Configuration file format:
	 *  First line: number of tests
	 *  Other lines: name of graph, expected result
	 *  For dfs, the result starts with the number of visited vertices, then the list of them
	 *  For hasCycle, the result starts with the number of vertices in the list, then the list, then the result
	 */

	/**
	 * Test method for {@link fr.uge.info2.algoadv.GraphAlgo#isTree(fr.uge.info2.algoadv.Graph)}.
	 */
	@Test
	void testHasSource() {
		String filepath = getResourcePath("hasSourceTest");
		try (var scanner = new Scanner(new File(filepath))) {
			int cases = scanner.nextInt();
			for (int i = 0; i < cases; i++) {
				String graphName = scanner.next(); 
				var g = loadGraph(graphName);
				var res = GraphAlgo.hasSource(g);
				if (res != scanner.nextBoolean()) {
					fail("Test hasSource fails on " + graphName + " with " + res);
				}
			}
		} catch (Exception e) {
			fail(e);
		}
	}

	/**
	 * Test method for {@link fr.uge.info2.algoadv.GraphAlgo#hasTriangle(fr.uge.info2.algoadv.Graph)}.
	 */
	@Test
	void testHasTriangle() {
		String filepath = getResourcePath("hasTriangleTest");
		try (var scanner = new Scanner(new File(filepath))) {
			int cases = scanner.nextInt();
			for (int i = 0; i < cases; i++) {
				String graphName = scanner.next(); 
				var g = loadGraph(graphName);
				var res = GraphAlgo.hasTriangle(g);
				if (res != scanner.nextBoolean()) {
					fail("Test hasTriangle fails on " + graphName + " with " + res);
				}
			}
		} catch (Exception e) {
			fail(e);
		}
	}

	/**
	 * Test method for {@link fr.uge.info2.algoadv.GraphAlgo#hasCycle(fr.uge.info2.algoadv.Graph)}.
	 */
	@Test
	void testHasCycle() {
		String filepath = getResourcePath("hasCycleTest");
		try (var scanner = new Scanner(new File(filepath))) {
			int cases = scanner.nextInt();
			for (int i = 0; i < cases; i++) {
				String graphName = scanner.next(); 
				var g = loadGraph(graphName);
				var res = GraphAlgo.hasCycle(g);
				if (res != scanner.nextBoolean()) {
					fail("Test hasCycle fails on " + graphName + " with " + res);
				}
			}
		} catch (Exception e) {
			fail(e);
		}
	}
	
	/**
	 * Test method for {@link fr.uge.info2.algoadv.GraphAlgo#dfs(fr.uge.info2.algoadv.Graph)}.
	 */
	@Test
	void testDfs() {
		String filepath = getResourcePath("dfsTest");
		try (var scanner = new Scanner(new File(filepath))) {
			int cases = scanner.nextInt();
			for (int i = 0; i < cases; i++) {
				String graphName = scanner.next();
				var g = loadGraph(graphName);
				int vertex = scanner.nextInt();
				int count = scanner.nextInt();
				var answer = new ArrayList<Integer>();
				for (int j = 0; j < count; j++) answer.add(scanner.nextInt());
				var res = GraphAlgo.dfs(g, vertex);
				if (!res.equals(answer)) {
					fail("Test dfs fails on " + graphName + " at " + vertex + " with " + res);
				}
			}
		} catch (Exception e) {
			fail(e);
		}
	}
	
	/**
	 * Test method for {@link fr.uge.info2.algoadv.GraphAlgo#isCycle(fr.uge.info2.algoadv.Graph)}.
	 */
	@Test
	void testIsCycle() {
		String filepath = getResourcePath("isCycleTest");
		try (var scanner = new Scanner(new File(filepath))) {
			int cases = scanner.nextInt();
			for (int i = 0; i < cases; i++) {
				String graphName = scanner.next();
				var g = loadGraph(graphName);
				int count = scanner.nextInt();
				var cycle = new ArrayList<Integer>();
				for (int j = 0; j < count; j++) cycle.add(scanner.nextInt());
				var answer = scanner.nextBoolean();
				var res = GraphAlgo.isCycle(g, cycle);
				if (res != answer) {
					fail("Test isCycle fails on " + graphName + " at " + cycle.toString() + " with " + res);
				}
			}
		} catch (Exception e) {
			fail(e);
		}
	}
	
	/**
	 * Test method for {@link fr.uge.info2.algoadv.GraphAlgo#findCycle(fr.uge.info2.algoadv.Graph)}.
	 */
	@Test
	void testFindCycle() {
		String filepath = getResourcePath("findCycleTest");
		try (var scanner = new Scanner(new File(filepath))) {
			int cases = scanner.nextInt();
			for (int i = 0; i < cases; i++) {
				String graphName = scanner.next();
				var g = loadGraph(graphName);
				var cycle = GraphAlgo.findCycle(g);
				if (cycle.size() == 0 && GraphAlgo.hasCycle(g)) {
					fail("Test findCycle fails on " + graphName +", failing to find an existing cycle");
				}
				if (cycle.size() != 0 && !GraphAlgo.isCycle(g, cycle)) {
					fail("Test findCycle fails on " + graphName + ", finding non-existing cycle " + cycle);
				}
			}
		} catch (Exception e) {
			fail(e);
		}
	}
}
