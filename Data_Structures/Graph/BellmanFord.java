package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Type:
 * Dynamic Programming
 * 
 * Goal:
 * Find the shortest path with minimum cost from one vertex to another in a graph.
 * 
 * Assumption:
 * 1. The graph is complete connected, meaning each vertex is connected with at least one vertex.
 * 2. Each vertex has only one best previous vertex, 
 *    meaning if same path cost from two vertices, choose one randomly.
 * 3. The cost is positive or negative.
 * 4. No self loop.
 * 5. Directed graph is necessary, otherwise may produce many negative cycles.
 * 
 * Complexity:
 * 1. Best: O(E), done in one iteration of all edges (a good order)
 * 2. Worst: O(E*V), done in (V-1) iterations of all edges (a bad order)
 */


public class BellmanFord {
	
    private Graph G;
	
	public BellmanFord(Graph graph) {
		this.G = graph;
	}
	
	public List<Vertex> ShortestPath(Vertex src, Vertex dest) {
		ShortestPathTree(src);
		return backtrace(src, dest);
	}
	
	public void ShortestPathTree(Vertex src) {
		
		// Step 1: Initialize distances from src to all other
        // vertices as INFINITE
		src.cost = 0;
		for (Vertex v:G.adjList.keySet()) {
			if (!v.equals(src))
				v.cost = Integer.MAX_VALUE;
		}
		
		// Step 2: Relax all edges |V| - 1 times. 
		// A simple shortest path from src to any other vertex can have at-most |V| - 1 edges
		// v := src, u := dest
		// Use updated to track if updates happen in one iteration.
		// If yes, keep going on; if not, terminate because nothing will
		// be updated in any of the rest iterations.
		boolean updated = true;
		for (int i = 1; i < G.adjList.size(); i++) {
			if (!updated) break;
			updated = false;
			for (Vertex v:G.adjList.keySet()) {
				if (v.cost == Integer.MAX_VALUE) continue; //this node hasn't been discovered
				for (Map.Entry<Vertex, Integer> entry:v.wneighbors.entrySet()) {
					Vertex u = entry.getKey();
					int w = entry.getValue();
					if (v.cost + w < u.cost) {
						u.cost = v.cost + w;
						u.prev = v;
						updated = true;
					}
				}
			}
		}
		
		// Step 3: check for negative-weight cycles. The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle (a cycle whose edges sum to a negative value)
		// If we get a shorter path, then there is a cycle.
		for (Vertex v:G.adjList.keySet()) {
			if (v.cost == Integer.MAX_VALUE) continue;
			for (Map.Entry<Vertex, Integer> entry:v.wneighbors.entrySet()) {
				Vertex u = entry.getKey();
				int w = entry.getValue();
				if (v.cost + w < u.cost) {
					System.out.println("Graph contains negative weight cycle");
				}
			}
		}
	}
	
	public List<Vertex> backtrace(Vertex src, Vertex dest) {
		List<Vertex> path = new ArrayList<>();
		while(!dest.equals(src)) {
			path.add(0, dest);
			dest = dest.prev;
		}
		path.add(0, src);
		return path;
	}
	
	public int showCost(Vertex v) {
		return v.cost;
	}
	
	
	public static void main(String[] args) {
		Graph g = new Graph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);
		Vertex v6 = new Vertex(6);
		g.addWDEdge(v1, v2, 5);
		g.addWDEdge(v1, v3, -2);
		g.addWDEdge(v2, v4, 1);
		g.addWDEdge(v3, v2, 2);
		g.addWDEdge(v4, v3, 2);
		g.addWDEdge(v3, v5, 3);
		g.addWDEdge(v4, v5, 7);
		g.addWDEdge(v4, v6, 3);
		g.addWDEdge(v5, v6, 10);
		
//		g.addWDEdge(v1, v2, -10);
//		g.addWDEdge(v2, v3, 3);
//		g.addWDEdge(v2, v4, -5);
//		g.addWDEdge(v3, v4, -8);
//		g.addWDEdge(v3, v6, 4);
//		g.addWDEdge(v4, v6, 5);
//		g.addWDEdge(v6, v5, -1);
//		g.addWDEdge(v5, v1, 7);
		BellmanFord b = new BellmanFord(g);
		for (Vertex v:b.ShortestPath(v1, v6))
			System.out.println(v.value);
	}

}
