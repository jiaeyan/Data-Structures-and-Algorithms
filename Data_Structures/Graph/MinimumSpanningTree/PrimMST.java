package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * [Prim's algorithm]
 * 
 * Type:
 * Greedy algorithm
 * 
 * Goal:
 * Find the minimum spanning tree of given undirected, weighted graph.
 * 
 * Approach:
 * Pretty the same as Dijkstra's algorithm, maintain a minimum heap that
 * contains all unused vertices in given graph, extract the one with minimum
 * edge weight every time, check its adjacency list, modify every neighbor's
 * parent and weight.
 * 
 * Complexity:
 * Space - O(E + V): store all edges and vertices of the graph
 * Time - O(ElogV): the loop checks each vertex and each edge of it, thus
 *                  takes O(V+E); each operation in min heap takes logV time,
 *                  so in total takes O((V+E)logV), since in connected graph
 *                  V = O(E), this algorithm takes O(ElogV)
 */
public class PrimMinSpanTree {
	
	private Graph G;
	private BinaryMinHeap heap;

	public PrimMinSpanTree(Graph graph) {
		this.G = graph;
		this.heap = new BinaryMinHeap(graph.adjList.size());
	}
	
	// This function initializes the minimum heap with all vertices
	// from the given graph. It assigns weight/cost of 0 to start point
	// and infinite to the rest vertices.
	public void fillHeap() {
		Iterator<Vertex> it = G.adjList.keySet().iterator();
		Vertex start = it.next();
		start.cost = 0;
		heap.insert(start);

		while(it.hasNext()) {
			Vertex v = it.next();
			v.cost = Integer.MAX_VALUE;
			heap.insert(v);
		}
	}
	
	// This function computes the MST. The result is a list of vertices,
	// each of which indicates where does it come from (parent).
	public List<Vertex> PrimMST() {
		fillHeap();
		List<Vertex> res = new ArrayList<>();
		
		// The MST should be composed of all vertices from given graph, so the
		// it won't stop until the heap is empty.
		while(!heap.isEmpty()) {
			
			// Get the vertex that has minimum cost/weight so far, add it to result.
			Vertex v = heap.extractMin();
			res.add(v);
			
			// Iterate over minimum vertex's neighbors.
			for(Vertex u:G.adjList.get(v)) {
				
				// If the neighbor is not in MST (in heap instead) and its former cost
				// is bigger than the edge weight from minimum vertex, update its weight
				// cost and also indicate where does it come from.
				int w = v.wneighbors.get(u);
				if(heap.contains(u) && u.cost > w) {
					u.prev = v;
					
					// Since the weight cost updates in the neighbor, re-arrange the heap
					// so it can be put in correct position in the heap.
					heap.decrease(heap.map.get(u), w);
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		Graph g = new Graph();
		
		Vertex v0 = new Vertex(0);
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);
		Vertex v6 = new Vertex(6);
		Vertex v7 = new Vertex(7);
		Vertex v8 = new Vertex(8);

		g.addWEdge(v0, v1, 4);
		g.addWEdge(v0, v7, 8);
		g.addWEdge(v1, v7, 11);
		g.addWEdge(v1, v2, 8);
		g.addWEdge(v7, v8, 7);
		g.addWEdge(v7, v6, 1);
		g.addWEdge(v2, v3, 7);
		g.addWEdge(v2, v5, 4);
		g.addWEdge(v8, v2, 2);
		g.addWEdge(v8, v6, 6);
		g.addWEdge(v6, v5, 2);
		g.addWEdge(v3, v4, 9);
		g.addWEdge(v3, v5, 14);
		g.addWEdge(v5, v4, 10);
		
		
		PrimMinSpanTree p = new PrimMinSpanTree(g);
		for(Vertex v:p.PrimMST()) {
			if(v.prev != null)
				System.out.println("Vertex: " + v.value + " Parent: " + v.prev.value);
			else 
				System.out.println("Vertex: " + v.value + " Parent: " + v.value);
		}
	}

}
