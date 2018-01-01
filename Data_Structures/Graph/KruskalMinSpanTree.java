package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * A minimum spanning tree (MST) for a weighted, connected and undirected graph 
 * is a spanning tree with weight less than or equal to the weight of every other 
 * spanning tree.
 * 
 * A mst has (V – 1) edges where V is the number of vertices in the given graph.
 * 
 * This class provides a function to compute the mst of a given graph.
 */

//[Kruskal's algorithm]
//
// Type:
// Greedy algorithm
//
// Goal:
// Find the minimum spanning tree of given undirected, weighted graph.
//
// Kruskal's algorithm is based on union-find algorithm.
// 1. Sort all edges in ascending order by weight.
// 2. Pick the edge that will not form a cycle (detected by union-find)
//    from the sorted edge list in order, and add it into the result.
// 3. Keep going until there is (V-1) edges in the result.
//
// Proof:
// Given graph has V vertices, MST has (V-1) edges and no cycle, so there must
// be V vertices in the MST when there is (V-1) edges.
//
// Complexity:
// Time complexity - O(ElogE) for sort edges, union-find takes too much less time
// Space complexity - O(E + V)

public class KruskalMinSpanTree {
	
	private Graph G;
	private List<Edge> edges;
	
	/*
	 * Since Kruskal's algorithm needs to perform on the edges of the graph,
	 * here we provide a class representing an weighted edge of a graph.
	 */
	public class Edge implements Comparable<Edge> {
		Vertex src;
		Vertex dest;
		int w;
		
		public Edge(Vertex src, Vertex dest, int weight) {
			this.src = src;
			this.dest = dest;
			this.w = weight;
		}

		@Override
		public int compareTo(Edge e) {
			return this.w - e.w;
		}
	}
	
	public KruskalMinSpanTree(Graph graph) {
		this.G = graph;
		this.edges = new ArrayList<>();
		
		// Form the edge list from the given graph's adjacency list.
		for(Vertex src:G.adjList.keySet()) {
			for(Vertex dest:G.adjList.get(src)) {
				edges.add(new Edge(src, dest, src.wneighbors.get(dest)));
			}
		}
	}
	
	public List<Edge> KruskalMST() {
		
		// Step 1: initialize each vertex's parent and rank.
		for(Vertex v:G.adjList.keySet()) {
			v.prev = v;
			v.rank = 0;
		}
		
		List<Edge> res = new ArrayList<>();
		
		// Step 2: sort the edges in ascending order.
		Collections.sort(edges);
		
		// Step 3: pick the edge with min weight that doesn't form a cycle.
		for(Edge edge:edges) {
			Vertex srcRoot = find(edge.src);
			Vertex destRoot = find(edge.dest);
			
			if(!srcRoot.equals(destRoot)) {
				res.add(edge);
				union(srcRoot, destRoot);
			}
			
			// The number of edges in MST should be (V-1).
			if(res.size() == G.adjList.size() - 1) break;
		}
		
		return res;
	}
	
	public Vertex find(Vertex v) {
		if(!v.prev.equals(v)) {
			v.prev = find(v.prev);
		}
		return v.prev;
	}
	
	public void union(Vertex src, Vertex dest) {
		if(src.rank < dest.rank) {
			src.prev = dest;
		}
		else if(src.rank > dest.rank) {
			dest.prev = src;
		}
		else {
			dest.prev = src;
			src.rank++;
		}
	}
	
	

	public static void main(String[] args) {
		Graph g = new Graph();
		
		Vertex v0 = new Vertex(0);
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		
		// Though mst only works for undirected graph,
		// we use directed graph structure to avoid duplicated double directed
		// edges in undirected graph.
		g.addWDEdge(v0, v1, 10);
		g.addWDEdge(v1, v3, 15);
		g.addWDEdge(v3, v2, 4);
		g.addWDEdge(v2, v0, 6);
		g.addWDEdge(v0, v3, 5);
		
		KruskalMinSpanTree mst = new KruskalMinSpanTree(g);
		
		List<Edge> res = mst.KruskalMST();
		for(Edge e:res) {
			System.out.println("Edge: " + e.src.value + " " + e.dest.value + " Weight: " + e.w);
		}
	}

}
