package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

//This class represents a directed graph using adjacency list representation
//Here assume the graph is all connected. Otherwise, need to get all vertices from this.adjList and do DFS/BFS for each.

public class Graph {
	
	private Map<Vertex, List<Vertex>> adjList;
	
	public Graph() {
		this.adjList = new HashMap<>();
	}
	
	public void addEdge(Vertex src, Vertex dest) {
		if (!this.adjList.containsKey(src)) 
			this.adjList.put(src, new LinkedList<>());
		if (!this.adjList.containsKey(dest))           
			this.adjList.put(dest, new LinkedList<>());
		this.adjList.get(src).add(dest);	
//		this.adjList.get(dest).add(src);  //comment out these lines if undirected graph is needed
	}
	
	//space save, the size of stack is the depth of the tree/graph,
	//but efficiency low if the target vertex is in the very right side of the tree/graph
	public void DFS_recursive(Vertex v) {
		if (!v.seen) {
			System.out.println(v.value);
			v.seen = true;
			for (Vertex w : this.adjList.get(v)) {
				DFS_recursive(w);
			}
		}
	}
	
	public void DFS_iterative(Vertex v) {
		Stack<Vertex> s = new Stack<>();
		s.push(v);
		while (!s.isEmpty()) {
			Vertex w = s.pop();
			if (!w.seen) {
				System.out.println(w.value);
				w.seen = true;
				for (Vertex z : this.adjList.get(w)) {
					s.push(z);
				}
			}
		}
	}
	
//	public void BFS_recursive(Vertex v) {} //recursive BFS has higher complexity than iterative, ignore this
	
	//space disaster, the size of the queue is the number of vertices at the same level, the more the higher
	//efficiency high
	public void BFS_iterative(Vertex v) {
		Queue<Vertex> q = new LinkedList<>();
		q.add(v);
		while (!q.isEmpty()) {
			Vertex w = q.poll();
			if (!w.seen) {
				System.out.println(w.value);
				w.seen = true;
				for (Vertex z : this.adjList.get(w)) {
					q.add(z);
				}
			}
		}
	}
	
	// Iterative deepening depth-first search, 
	// combines depth-first search's space-efficiency and breadth-first search's completeness
	// the v should be one of the centers of the graph
	public boolean IDDFS(Vertex v, Vertex target, int depth) {
		for (int i = 0; i < depth; i++) {
			if (DLS(v, target, i)) return true;
		}
		return false;
	}
	
	//depth-limited DFS
	public boolean DLS(Vertex v, Vertex target, int depth) {
		if (v.value == target.value) {return true;}
		if (depth <= 0) {
			return false;
		}
		for (Vertex w : this.adjList.get(v)) {
			if (DLS(w, target, depth - 1)) return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		Graph g = new Graph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);
		Vertex v6 = new Vertex(6);
		g.addEdge(v1, v2);
		g.addEdge(v2, v3);
		g.addEdge(v2, v4);
		g.addEdge(v3, v4);
		g.addEdge(v3, v6);
		g.addEdge(v6, v5);
		g.addEdge(v5, v1);
		System.out.println(g.IDDFS(v1, v2, 2));
//		g.DFS_recursive(v1);
//		g.DFS_iterative(v1);
//		g.BFS_iterative(v1);
	}

}
