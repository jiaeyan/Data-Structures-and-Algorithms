package graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
 * Type:
 * Dynamic programming
 * 
 * Goal:
 * Solves the All Pairs Shortest Path problem. The problem is to 
 * find shortest distances between every pair of vertices in a 
 * given edge weighted directed Graph.
 * 
 * Complexity:
 * Time - O(V^3): 3 level nested for loops
 * Space - O(V^2): adjacency matrix of V * V
 */
public class FloydWarshall {
	
	private Graph G;
	private int[][] dist;
	private int[][] path;
	private Map<Integer, Vertex> map;
	private static final int INF = 1000000;
	
	// This algorithm only works on adjacency matrix, so need to convert
	// adjacency list ro matrix. 
	
	// The dist matrix records shortest distances
	// so far for each pair vertices. E.g.: dist[src][dest] indicates shortest
	// distance from src to dest so far.
	//
	// The path matrix records the parent vertex of current cell,
	// E.g. path[src][dest] indicates the parent vertex of this cell.
	public FloydWarshall(Graph graph) {
		this.G = graph;
		int n = G.adjList.size();
		
		dist = new int[n][n];
		path = new int[n][n];
		
		// All distances are initialized as big number.
		// All parent vertices are initialized as -1.
		for(int i = 0; i < n; i++) {
			Arrays.fill(dist[i], INF);
			Arrays.fill(path[i], -1);
		}
		
		map = new HashMap<>();
		for(Vertex v:G.adjList.keySet()) {
			map.put(v.value, v);
		}
		createMatrix();
	}
	
	// Convert adjacency list to adjacency matrix.
	public void createMatrix() {
		for(Vertex v:G.adjList.keySet()) {
			for(Map.Entry<Vertex, Integer> entry:v.wneighbors.entrySet()) {
				Vertex u = entry.getKey();
				int w = entry.getValue();
				int src = v.value;
				int dest = u.value;
//				System.out.println("src: " + src + " dest: " + dest + " weight: " + w);
				dist[src][dest] = w;
				path[src][dest] = src;
			}
		}
	}
	
	public void ShortestPath() {
		int n = G.adjList.size();
		
		// We make every vertex as intermediate vertex of every pair vertices.
		// k: the intermediate vertex between src and dest.
		// i: the src vertex
		// j: the dest vertex
		for(int k = 0; k < n; k++) {
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					if(dist[i][k] == INF || dist[k][j] == INF) {
                        continue;
                    }
					
					// Since k is between i and j, if the distance sum of i to k and
					// k to j is smaller than distance from i to j, update the distance
					// from i to j, then also update the parent vertex of j, meaning
					// i to j is longer than i to k then k to j, so parent of j updates
					// to k from i.
					if(dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						path[i][j] = path[k][j];
					}
				}
			}
		}
		NegativeCycle();
	}
	
	// Distance of any node from itself is always zero. But in some cases, 
	// as in this example, when traverse further, the distance may be negative.
	public void NegativeCycle() {
		for(int i = 0; i < G.adjList.size(); i++) {
            if(dist[i][i] < 0) {
                System.out.println("Negative cycle exists.");
            }
        }
	}
	
	public void printDist() {
		int n = G.adjList.size();
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(dist[i][j] != INF)
					System.out.print(dist[i][j] + " ");
				else
					System.out.print("INF" + " ");
			}
			System.out.println();
		}
	}
	
	public void printPath(Vertex src, Vertex dest) {
		int start = src.value;
		int end = dest.value;
		Stack<Integer> stack = new Stack<>();
		stack.push(end);
		
		while(true) {
			end = path[start][end];
			if(end == -1) return;
			stack.push(end);	
			if(start == end) break;
		}
		
		while(!stack.isEmpty()) {
			System.out.print(stack.pop() + " ");
		}
	}

	public static void main(String[] args) {
		Graph g = new Graph();
		Vertex v0 = new Vertex(0);
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
//		Vertex v4 = new Vertex(4);
//		Vertex v5 = new Vertex(5);
//		Vertex v6 = new Vertex(6);
		
//		g.addWDEdge(v0, v3, 10);
//		g.addWDEdge(v0, v1, 5);
//		g.addWDEdge(v1, v2, 3);
//		g.addWDEdge(v2, v3, 1);
//		g.addWDEdge(v2, v0, -2);
		
		g.addWDEdge(v0, v3, 15);
		g.addWDEdge(v3, v0, 1);
		g.addWDEdge(v0, v1, 3);
		g.addWDEdge(v1, v2, -2);
		g.addWDEdge(v0, v2, 6);
		g.addWDEdge(v2, v3, 2);
		
		FloydWarshall fw = new FloydWarshall(g);
		fw.ShortestPath();
		
		fw.printDist();
		System.out.println();
		fw.printPath(v0, v3);
	}

}
