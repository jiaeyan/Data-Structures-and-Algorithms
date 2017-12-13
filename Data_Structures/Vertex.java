package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vertex {
	int value;        //this is the name or value of the vertex
	boolean seen;     //this is to record if the vertex has been discovered during traversal
	Vertex prev;
	int cost;         //this is for dijkstra algorithm, the cost sum till this vertex, which will be initialized externally.
	List<Vertex> neighbors;
	Map<Vertex, Integer> wneighbors;
	
	public Vertex(int value) {
		this.value = value;
		this.seen = false;
		this.neighbors = new ArrayList<>();
		this.wneighbors = new HashMap<>();
	}
}
