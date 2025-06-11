package fr.uge.info2.algoadv;

import java.util.ArrayList;

public class GraphAlgo {
    
    /**
     * Returns whether the given directed graph contains a sink,
     * i.e., a vertex with no outgoing edge to any other vertex (loops allowed).
     * Complexity: O(V + E)
     */
    public static boolean hasSink(Graph g) {
        int n = g.numberOfVertices();
        for (int u = 0; u < n; u++) {
            boolean hasOutgoingToOther = false;
            var it = g.edgeIterator(u);
            while (it.hasNext()) {
                var e = it.next();
                int v = e.getEnd();
                if (v != u) {
                    // found an outgoing edge to a different vertex
                    hasOutgoingToOther = true;
                    break;
                }
            }
            if (!hasOutgoingToOther) {
                // no outgoing to any other vertex, u is a sink
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether the directed graph has any oriented triangle (3 distinct vertices u,v,w with u->v, v->w, w->u).
     * Complexity: O(V^3)
     */
    public static boolean hasTriangle(Graph g) {
        int n = g.numberOfVertices();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if (!g.isEdge(i, j)) continue;
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;
                    if (g.isEdge(j, k) && g.isEdge(k, i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the list of vertices visited by DFS starting at vertex v.
     * Neighbors are visited in the order given by edgeIterator.
     * Complexity: O(V + E)
     */
    public static ArrayList<Integer> dfs(Graph g, int v) {
        int n = g.numberOfVertices();
        boolean[] visited = new boolean[n];
        ArrayList<Integer> order = new ArrayList<>();
        dfsVisit(g, v, visited, order);
        return order;
    }

    private static void dfsVisit(Graph g, int u, boolean[] visited, ArrayList<Integer> order) {
        if (visited[u]) return;
        visited[u] = true;
        order.add(u);
        var it = g.edgeIterator(u);
        while (it.hasNext()) {
            int w = it.next().getEnd();
            if (!visited[w]) {
                dfsVisit(g, w, visited, order);
            }
        }
    }

    /**
     * Returns whether the directed graph has any cycle (including loops). Complexity: O(V + E)
     */
    public static boolean hasCycle(Graph g) {
        int n = g.numberOfVertices();
        int[] state = new int[n]; // 0=white, 1=gray, 2=black
        for (int u = 0; u < n; u++) {
            if (state[u] == 0) {
                if (dfsHasCycle(g, u, state)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfsHasCycle(Graph g, int u, int[] state) {
        state[u] = 1; // gray
        var it = g.edgeIterator(u);
        while (it.hasNext()) {
            int w = it.next().getEnd();
            if (w == u) {
                // loop counts as cycle
                return true;
            }
            if (state[w] == 0) {
                if (dfsHasCycle(g, w, state)) {
                    return true;
                }
            } else if (state[w] == 1) {
                // back-edge found
                return true;
            }
        }
        state[u] = 2; // black
        return false;
    }

    /**
     * Returns whether the given list of vertices forms an oriented cycle in the graph.
     * If the list is empty, returns false.
     */
    public static boolean isCycle(Graph g, ArrayList<Integer> vertexList) {
        int m = vertexList.size();
        if (m == 0) return false;
        if (m == 1) {
            int u = vertexList.get(0);
            return g.isEdge(u, u);
        }
        int n = g.numberOfVertices();
        boolean[] seen = new boolean[n];
        for (int x : vertexList) {
            if (x < 0 || x >= n) return false;
            if (seen[x]) return false;
            seen[x] = true;
        }
        for (int i = 0; i < m - 1; i++) {
            int u = vertexList.get(i);
            int v = vertexList.get(i + 1);
            if (!g.isEdge(u, v)) return false;
        }
        int last = vertexList.get(m - 1);
        int first = vertexList.get(0);
        return g.isEdge(last, first);
    }

    /**
     * Returns any oriented cycle in the graph; if none exists, returns an empty list. Complexity: O(V + E)
     */
    public static ArrayList<Integer> findCycle(Graph g) {
        int n = g.numberOfVertices();
        int[] state = new int[n]; // 0=white, 1=gray, 2=black
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = -1;
        for (int u = 0; u < n; u++) {
            if (state[u] == 0) {
                ArrayList<Integer> cycle = dfsFindCycle(g, u, state, parent);
                if (!cycle.isEmpty()) {
                    return cycle;
                }
            }
        }
        return new ArrayList<>();
    }

    private static ArrayList<Integer> dfsFindCycle(Graph g, int u, int[] state, int[] parent) {
        state[u] = 1; // gray
        var it = g.edgeIterator(u);
        while (it.hasNext()) {
            int w = it.next().getEnd();
            if (w == u) {
                ArrayList<Integer> single = new ArrayList<>();
                single.add(u);
                return single;
            }
            if (state[w] == 0) {
                parent[w] = u;
                ArrayList<Integer> cycle = dfsFindCycle(g, w, state, parent);
                if (!cycle.isEmpty()) {
                    return cycle;
                }
            } else if (state[w] == 1) {
                ArrayList<Integer> cycle = new ArrayList<>();
                cycle.add(w);
                int x = u;
                while (x != w) {
                    cycle.add(x);
                    x = parent[x];
                }
                return cycle;
            }
        }
        state[u] = 2; // black
        return new ArrayList<>();
    }
}