package graph;

import java.util.Stack;


/*
 * Goal:
 * For every directed edge uv from vertex u to vertex v, 
 * u comes before v in the ordering.
 * Multiple possible solutions exist, any vertices share no edges can be swapped. 
 * 
 * Condition:
 * The graph should be a directed acyclic graph (DAG).
 * 
 * Complexity:
 * O(E+V), you reach every vertex, and every time you visit a vertex, you cancel
 * the edge to that vertex, so you go through every node and every edge once.
 */

public class TopologicalSorting {
	
	private Graph G;
	
	public TopologicalSorting(Graph graph) {
		this.G = graph;
	}
	
	/*
	 * DFS based:
	 * Iterate over each unseen node, mark as seen,
	 * then iterate over all its neighbors recursively,
	 * until reach a vertex has no out-going edges,
	 * push that vertex into a stack, meaning this is the
	 * very bottom node (since no cycle, there must be some
	 * vertices having no out-going edges). Do this bottom-up,
	 * the top ones will be the ones that have no in-coming edges. 
	 * 
	 * Another approach is to maintain a set of nodes that
	 * have no in-coming edges, level by level find their children.
	 */
	public void sort() {
		Stack<Vertex> s = new Stack<>();
		for(Vertex v:G.adjList.keySet()) {
			if(!v.seen) {
				visit(v, s);
			}
		}
		
		while(!s.isEmpty()) 
			System.out.println(s.pop().value);
	}
	
	public void visit(Vertex v, Stack<Vertex> s) {
		v.seen = true;
		for(Vertex u:G.adjList.get(v)) {
			if(!u.seen)
				visit(u, s);
		}
		s.push(v);
	}
	
	public static void main(String[] args) {
		Graph g = new Graph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);
		Vertex v0 = new Vertex(0);
		
//		g.addDEdge(v5, v2);
//		g.addDEdge(v5, v0);
//		g.addDEdge(v4, v0);
//		g.addDEdge(v4, v1);
//		g.addDEdge(v2, v3);
//		g.addDEdge(v3, v1);
		
		g.addDEdge(v0, v1);
		g.addDEdge(v0, v2);
		g.addDEdge(v0, v3);
		g.addDEdge(v1, v4);
		g.addDEdge(v2, v4);
		g.addDEdge(v2, v5);
		g.addDEdge(v3, v5);
		
		TopologicalSorting ts = new TopologicalSorting(g);
		ts.sort();
	}

}
