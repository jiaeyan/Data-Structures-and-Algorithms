package graph;

public class UnionFindCycle {
	
	private Graph G;
	
	public UnionFindCycle(Graph graph) {
		this.G = graph;
		for(Vertex v:G.adjList.keySet()) {
			v.prev = v;
			v.rank = 0;
		}
	}
	
	public Vertex find(Vertex v) {
		if(v.prev != v) {
			v.prev = find(v.prev);
		}
		return v.prev;
	}
	
	public void union(Vertex src, Vertex dest) {
		Vertex srcRoot = find(src);
		Vertex destRoot = find(dest);
		
		if(!srcRoot.equals(destRoot)) {
			if(srcRoot.rank < destRoot.rank) {
				srcRoot.prev = destRoot;
			}
			else if(srcRoot.rank > destRoot.rank) {
				destRoot.prev = srcRoot;
			}
			else {
				destRoot.prev = srcRoot;
				srcRoot.rank++;
			}
		}
	}
	
	public boolean cycle() {
		for(Vertex src:G.adjList.keySet()) {
			for(Vertex dest:G.adjList.get(src)) {
				Vertex srcRoot = find(src);
				Vertex destRoot = find(dest);
				
				if(srcRoot.equals(destRoot)) {
					return true;
				}
				else {
					union(srcRoot, destRoot);
				}
			}
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
		
		g.addDEdge(v1, v3);
		g.addDEdge(v3, v5);
		g.addDEdge(v2, v1);
		g.addDEdge(v2, v3);
		g.addDEdge(v4, v2);
		g.addDEdge(v5, v4);
		g.addDEdge(v3, v4);
		
		UnionFindCycle ufc = new UnionFindCycle(g);
		System.out.println(ufc.cycle());
	}

}
