package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Cycle {
	
	private Graph G;
	
	public Cycle(Graph graph) {
		this.G = graph;
	}
	
	/*
	 * This function prints all cycles in a directed graph.
	 * DFS based brutal force approach to find all cycles in a directed graph.
	 * TBD: Tarjan's, Johnson's and other algorithms for directed graph.
	 */
	public void directedCycle() {
		// Solo check seen is not enough, need a list to track a cycle
		List<Vertex> cycle = new ArrayList<>();
		
		// Track all distinct cycles, get rid of same cycle
		// with different permutations of vertices later on
		Set<Set<Vertex>> cycles = new HashSet<>();
		
		// To find all cycles, need to go through all DFS trees, 
		// aka start from all vertices, and make them the start points
		// of cycles. If start point detected again later on, cycle.
		// This leads to duplication solutions, since every vertex in one cycle
		// produces the same cycle.
		// Can't just record all vertices in a cycle and ignore them later on,
		// since they may produce different cycles with less vertices in the cycle.
		// So the only way is to check vertex by vertex.
		for(Vertex v:G.adjList.keySet()) {
			directedUtil(v, cycle, cycles);
		}
	}
	
	public void directedUtil(Vertex v, List<Vertex> cycle, Set<Set<Vertex>> cycles) {
		if(!v.seen) {
			cycle.add(v);
			v.seen = true;
			for(Vertex u:G.adjList.get(v)) {
				// Make a set of vertices in current cycle, avoiding 
				// same cycle with different permutations of vertices
				Set<Vertex> tempCycle = new HashSet<>(cycle);
				// If next vertex is the start point, a cycle; also check if the same
				// cycle with different permutation of vertices already in cycles.
				if(cycle.get(0).equals(u) && !cycles.contains(tempCycle)) {
					cycles.add(tempCycle);
					showCycle(cycle, u);
				}
				// DFS
				directedUtil(u, cycle, cycles);
			}
			// Backtrack
			cycle.remove(v);
			v.seen = false;
		}
	}
	
	public void showCycle(List<Vertex> cycle, Vertex u) {
		for(Vertex v:cycle) {
			System.out.print(v.value);
		}
		System.out.println(u.value);
	}
	
	/*
	 * This function prints all cycles in an indirected graph.
	 * The idea is the same as directed one, only difference is that
	 * for directed graph, v1 -> v2 and v1 <- v2 forms a cycle, while in indirected
	 * graph v1 -> v2 and v1 <- v2 not, so need a way to get rid of two-vertices-cycle.
	 */
	public void indirectedCycle() {
		List<Vertex> cycle = new ArrayList<>();
		Set<Set<Vertex>> cycles = new HashSet<>();
		for(Vertex v:G.adjList.keySet()) {
			indirectedUtil(v, null, cycle, cycles);
		}
	}
	
	// Here we pass a 'parent' vertex to ensure current vertex
	// ignores its parent when doing DFS, to get rid of 2-v-cycle
	public void indirectedUtil(Vertex v, Vertex parent, List<Vertex> cycle, Set<Set<Vertex>> cycles) {
		if(!v.seen) {
			cycle.add(v);
			v.seen = true;
			for(Vertex u:G.adjList.get(v)) {
				if(u.equals(parent)) continue;
				Set<Vertex> tempCycle = new HashSet<>(cycle);
				if(cycle.get(0).equals(u) && !cycles.contains(tempCycle)) {
					cycles.add(tempCycle);
					showCycle(cycle, u);
				}
				indirectedUtil(u, v, cycle, cycles);
			}
			cycle.remove(v);
			v.seen = false;
		}
	}
	
	public static void main(String[] args) {
		Graph g = new Graph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);

//		g.addDEdge(v1, v3);
//		g.addDEdge(v3, v5);
//		g.addDEdge(v2, v1);
//		g.addDEdge(v2, v3);
//		g.addDEdge(v4, v2);
//		g.addDEdge(v5, v4);
//		g.addDEdge(v3, v4);
		
//		g.addDEdge(v1, v3);
//		g.addDEdge(v3, v5);
//		g.addDEdge(v2, v1);
//		g.addDEdge(v3, v2);
//		g.addDEdge(v4, v2);
//		g.addDEdge(v4, v5);
//		g.addDEdge(v3, v4);
//		g.addDEdge(v5, v2);
		
		g.addEdge(v1, v3);
		g.addEdge(v3, v5);
		g.addEdge(v2, v1);
		g.addEdge(v3, v2);
		g.addEdge(v4, v2);
		g.addEdge(v4, v5);
		g.addEdge(v3, v4);
		g.addEdge(v1, v4);
				
		Cycle c = new Cycle(g);
		c.indirectedCycle();
	}

}
