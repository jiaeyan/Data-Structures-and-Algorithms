package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
 * Assumption:
 * Any DAG has at least one vertex that has no in-coming edges (tops) and at least one
 * vertex that has no out-going edges (bottoms).
 * 
 * Complexity:
 * O(E+V), you reach every vertex, and every time you visit a vertex, you cancel
 * the edge to that vertex, so you go through every node and every edge once.
 */

public class TopoSort {
	
	private Graph G;
	
	public TopoSort(Graph graph) {
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
	 */
	public void DFS_sort() {
		Stack<Vertex> s = new Stack<>();
		for(Vertex v:G.adjList.keySet()) {
			if(!v.seen)
				visit(v, s);
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
	
	 /* 
	 * Kahn's based:
	 * Another approach is to maintain a set of nodes that
	 * have no in-coming edges (0 indeggree), level by level find their children.
	 */
	public void Kahn_sort() {
		// Create a map to store indegrees of all vertices
		// Traverse adjacency lists to fill indegrees of vertices. This step takes O(V+E) time
		Map<Vertex, Integer> indegree = fillDegree();
		
		// Return a queue where all elements in it has no in-coming edges.
		Queue<Vertex> noDegree = find0Degree(indegree);
		
		// Create a list to store result (A topological ordering of the vertices)
		List<Vertex> res = new ArrayList<>();
		
		while(!noDegree.isEmpty()) {
			// Extract front of queue and add it to topological order
			Vertex v = noDegree.poll();
			res.add(v);
			
			// Iterate through all its neighboring nodes
            // of dequeued node v and decrease their in-degree by 1 (removing relevant edges)
			for(Vertex u:G.adjList.get(v)) {
				indegree.replace(u, indegree.get(u) - 1);
				// If in-degree becomes zero, add it to queue
				if(indegree.get(u) == 0) {
					noDegree.add(u);
				}
			}
		}
		for(Vertex v:res) {
			System.out.println(v.value);
		}
	}
	
	public Map<Vertex, Integer> fillDegree() {
		Map<Vertex, Integer> indegree = new HashMap<>();
		for(Vertex v:G.adjList.keySet()) {
			indegree.put(v, 0);
		}
		for(List<Vertex> nbrs:G.adjList.values()) {
			for(Vertex v:nbrs) {
				indegree.replace(v, indegree.get(v) + 1);
			}
		}
		return indegree;
	}
	
	public Queue<Vertex> find0Degree(Map<Vertex, Integer> indegree) {
		Queue<Vertex> noDegree = new LinkedList<>();
		for(Map.Entry<Vertex, Integer> entry:indegree.entrySet()) {
			if(entry.getValue() == 0) {
				noDegree.add(entry.getKey());
			}
		}
		return noDegree;
	}
	
	/*
	 * This function prints all possible solutions of topo sorts.
	 * The main idea is based on Kahn's algorithm, keep finding
	 * top vertices (with 0 in-coming edges, may due to reduction),
	 * and combine backtracking (add, recurse, remove) to get all solutions.
	 */
	public void allSorts() {
		Map<Vertex, Integer> indegree = fillDegree();
		List<Vertex> res = new ArrayList<>();
		allUtil(indegree, res);
	}
	
	public void allUtil(Map<Vertex, Integer> indegree, List<Vertex> res) {
		for(Vertex v:indegree.keySet()) {
		    // Choose a qualified vertex as top, all these vertices will be discovered
			// at this level, aka the same level.
			if(!v.seen && indegree.get(v) == 0) {
				res.add(v);
				v.seen = true;
			    // Reduce in-degree of adjacent vertices
				for(Vertex u:G.adjList.get(v)) {
					indegree.replace(u, indegree.get(u) - 1);
				}
				// One top down, find next top, since makred as seen, next call
				// will ignore current v.
				allUtil(indegree, res);
				
				// Prepare for next possible same level vertex, recover all changes.
				res.remove(v);
				v.seen = false;
				for(Vertex u:G.adjList.get(v)) {
					indegree.replace(u, indegree.get(u) + 1);
				}
			}
		}
	    //  Once all vertices are visited, print the solution.
		if(res.size() == indegree.size()) {
			for(Vertex v:res)
				System.out.print(v.value);
			System.out.println();
		}
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
		
		TopoSort ts = new TopoSort(g);
//		ts.DFS_sort();
//		System.out.println();
//		ts.Kahn_sort();
		ts.allSorts();
	}

}
