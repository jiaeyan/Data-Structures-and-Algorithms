package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Goal:
 * find the shortest path with minimum cost from one vertex to another in a graph.
 * 
 * Assumption:
 * 1. the graph is complete connected, meaning each vertex is connected with at least one vertex.
 * 2. each vertex has only one best previous vertex, 
 * meaning if same path cost from two vertices, choose one randomly.
 * 3. the cost is positive
 * 
 * Complexity: 
 * 1. linear search in unseen set Q: O(|E|+|V|^2) = O(|V|^2).
 * 2. binary heap search in unseen set Q: O((E + V)logV)
 */

public class Dijkstra {
	
	private Graph G;
	
	public Dijkstra(Graph graph) {
		this.G = graph;
	}
	
	public List<Vertex> ShortestPath(Vertex src, Vertex dest) {
		ShortestPathTree(src);
		return showPath(dest);
	}
	
	/*
	 * Compute shortest distances from source to every vertex in graph.
	 */
	public void ShortestPathTree(Vertex src) {
		//Set source vertex cost as 0, all others as max.
		//Put all vertices in a set, extract the one has minimum current cost every time.
		src.cost = 0;
		Set<Vertex> Q = new HashSet<>(Arrays.asList(new Vertex[]{src}));
		
		for (Vertex v:G.adjList.keySet()) {
			if (!v.equals(src)) {
				v.cost = Integer.MAX_VALUE;
				Q.add(v);
			}
		}
		
		while (!Q.isEmpty()) {
			//This s will always be the source for next operation.
			//Always start from the vertex that has minimum current cost.
			//But still have to iterate over all vertices to mark their final minimum cost.
			//Since every extracted vertex already has the minimum current path cost from source,
			//there is no need to reconsider other possibility for it, so kick it out
			//from the unseen set, so will not modify its path cost later on.
			Vertex s = findMin(Q);
			//if (s.equals(dest)) return; if only need to traverse to dest vertex
			for (Vertex v:G.adjList.get(s)) {
				if (!v.seen) { //or Q.contains(v);
					int cost = s.cost + s.wneighbors.get(v);
					if (cost < v.cost) {
						v.cost = cost;
						v.prev = s;
					}
				}
			}
			s.seen = true;
		}
	}
	
	/*
	 * Linear search in the unseen set.
	 * This can be optimized to be a min priority queue, extract min every time,
	 * then shuffle the queue every time when the cost of a vertex decreases.
	 */
	public Vertex findMin(Set<Vertex> Q) {
//		Queue<Vertex> Q = new PriorityQueue<>(new Comparator<Vertex>() {
//		public int compare(Vertex v1, Vertex v2) {return v1.value - v2.value;}
//	});
		Vertex target = null;
		int min = Integer.MAX_VALUE;
		for (Vertex v:Q) {
			if (v.cost < min) {
				target = v;
				min = v.cost;
			}
		}
		Q.remove(target);
		return target;
	}
	
	public List<Vertex> showPath(Vertex v) {
		List<Vertex> path = new ArrayList<>(Arrays.asList(new Vertex[]{v}));
		while(v.prev != null) {
			path.add(0, v.prev);
			v = v.prev;
		}
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
		g.addWDEdge(v1, v2, 10);
		g.addWDEdge(v2, v3, 3);
		g.addWDEdge(v2, v4, 5);
		g.addWDEdge(v3, v4, 8);
		g.addWDEdge(v3, v6, 4);
		g.addWDEdge(v4, v6, 5);
		g.addWDEdge(v6, v5, 1);
		g.addWDEdge(v5, v1, 7);
		Dijkstra d = new Dijkstra(g);
		for (Vertex v:d.ShortestPath(v1, v6))
			System.out.println(v.value);
	}

}
