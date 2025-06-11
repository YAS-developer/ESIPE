package fr.uge.info2.algoadv;

import java.util.ArrayList;
import java.util.List;

public class GraphAlgo {

  public static boolean hasSource(Graph g) {
    /* */
    int n = g.numberOfVertices();
    for (int i = 0; i < n; i++) {
      boolean isSource = true;
      for (int j = 0; j < n; j++) {
        if (i != j && g.isEdge(j, i)) {
          isSource = false;
          break;
        }
      }
      if (isSource) {
        return true;
      }
    }
    return false;
  }


  public static boolean hasTriangle(Graph g) {
    // TODO: returns whether the graph has any oriented triangle in it.
    // The three vertices of the triangle must all be different
    // Complexity should be O(V^3)
    int n = g.numberOfVertices();

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i == j || !g.isEdge(i, j)) continue;

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

  private static void recDFS(Graph g, int s, boolean[] visited, List<Integer> list) {
    visited[s] = true;
    list.add(s);
    g.forEachEdge(s, edge -> {
      var t = edge.getEnd();
      if(!visited[t]) {
        recDFS(g, t, visited, list);
      }
    });
  }

  public static ArrayList<Integer> dfs(Graph g, int v) {
    // TODO: return the list of vertices visited by DFS starting from v. 
    // Neighbors should be visited in the order given by edge iterator.
    // Complexity should be O(V + E)
    var n = g.numberOfVertices();
    var visited = new boolean[n];
    var list = new ArrayList<Integer>();

    recDFS(g, v, visited, list);


    return list;
  }

  private static boolean hasCycleDFS(Graph g, int v, boolean[] visited, boolean[] visiting) {
    visited[v] = true;
    visiting[v] = true;

    var it = g.edgeIterator(v);

    while(it.hasNext()) {
      var neighboor = it.next().getEnd();
      if(neighboor == v) return true;
      if(!visited[neighboor]) {
        if(hasCycleDFS(g, neighboor, visited, visiting)) return true;
      }else if(visiting[neighboor]) {
        return true;
      }
    }
    visiting[v] = false;
    return false;
  }

  public static boolean hasCycle(Graph g) {
    // TODO: return whether the graph has a cycle (loops are also counted as cycles)
    // Complexity should be O(V + E)
    var n = g.numberOfVertices();
    var visited = new boolean[n];
    var visiting = new boolean[n];

    for(var i = 0; i < n; i++) {
      if(!visited[i] && hasCycleDFS(g, i, visited, visiting)) {
        return true;
      }
    }

    return false;
  }

  public static boolean isCycle(Graph g, ArrayList<Integer> vertexList) {
    // TODO: return whether the given list of vertices in the order is an oriented cycle of the given graph
    // If the list is empty, the function should return false.

    for(var i = 0; i < vertexList.size() -1 ; i++) {
      var from  = vertexList.get(i);
      var to = vertexList.get(i + 1);
      if(!g.isEdge(from, to)) {
        return false;
      }
    }

    var last = vertexList.get(vertexList.size() - 1);
    var first = vertexList.get(0);
    return g.isEdge(last, first);
  }

  private static boolean findCycleDFS(Graph g, int v, boolean[] visited, boolean[] visiting, List<Integer> path, List<Integer> list){
    visited[v] = true;
    visiting[v] = true;
    path.add(v);

    var it = g.edgeIterator(v);

    while(it.hasNext()) {
      var t = it.next().getEnd();

      if(v == t) {
        list.add(v);
        list.add(v);
        return true;
      }

      if(!visited[t]) {
        if(findCycleDFS(g, t, visited, visiting, path, list)) {
          return true;
        }
      }else if(visiting[t]) {
        int index = path.indexOf(t);
        for (int i = index; i < path.size(); i++) {
          list.add(path.get(i));
        }
        return true;
      }

    }
    visiting[v] = false;
    path.remove(path.size() - 1);
    return false;
  }

  public static ArrayList<Integer> findCycle(Graph g) {
    // TODO: return any oriented cycle in the given graph. If none exists, returns an empty list
    var n = g.numberOfVertices();
    var visited = new boolean[n];
    var visiting = new boolean[n];
    var path = new ArrayList<Integer>();
    var list = new ArrayList<Integer>();
    for(var i = 0; i < n ; i++) {
      if(!visited[i] && findCycleDFS(g, i, visited, visiting, path, list)) {
        return list;
      }
    }
    return list;
  }
}
