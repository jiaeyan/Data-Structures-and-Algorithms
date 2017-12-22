package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BellmanFord {
	
    private Graph G;
	
	public BellmanFord(Graph graph) {
		this.G = graph;
	}
	
	public void ShortestPathTree(Vertex src) {
		
		// Step 1: Initialize distances from src to all other
        // vertices as INFINITE
		src.cost = 0;
		for (Vertex v:G.adjList.keySet()) {
			if (!v.equals(src))
				v.cost = Integer.MAX_VALUE;
		}
		
		// Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
		for (int i = 0; i < G.adjList.size(); i++) {
			for (Vertex v:G.adjList.keySet()) {
				for (Map.Entry<Vertex, Integer> entry:v.wneighbors.entrySet()) {
					Vertex u = entry.getKey();
					int w = entry.getValue();
					if (u.cost + w < v.cost) {
						v.cost = u.cost + w;
						v.prev = u;
					}
				}
			}
		}
	}
	
	public List<Vertex> backtrace(Vertex v) {
		List<Vertex> path = new ArrayList<>(Arrays.asList(new Vertex[]{v}));
		while(v.prev != null) {
			path.add(0, v.prev);
			v = v.prev;
		}
		return path;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
