package graph;

public class Vertex {
	int value;
	boolean seen;     //this is to record if the vertex has been discovered during traversal
	
	public Vertex(int value) {
		this.value = value;
		this.seen = false;
	}
}
