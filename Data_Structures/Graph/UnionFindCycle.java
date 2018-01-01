package graph;

/*
 * Union find algorithm:
 * A forest-like data structure, each of whose elements belongs to a set that is
 * represented as a tree. It has following two operations:
 * 
 * 1. Find(x): find the representative element (root) of the set that has x;
 *             So in each set the root is able to reach every other element in the 
 *             same set.
 * > path compression: link each element in one set directly to the representative
 *                     element of that set to reduce backtracking time.
 *                     
 * 2. Union(x, y): if the representative elements of x and y are different,
 *                a.k.a. x and y are in different sets, union the two sets.
 * > union by rank: each representative element (root) in a set has a rank, and 
 *                  union by rank, generally attach lower rank set to higher one,
 *                  to reduce the depth of the tree as much as possible.
 *      
 */

public class UnionFindCycle {
	
	private Graph G;
	
	public UnionFindCycle(Graph graph) {
		this.G = graph;
		
		// At the start, each vertex forms a set.
		// The root's parent is itself.
		// All vertices's rank is 0.
		for(Vertex v:G.adjList.keySet()) {
			v.prev = v;
			v.rank = 0;
		}
	}
	
	public Vertex find(Vertex v) {
		// Path compression, link v directly to its root.
		if(!v.prev.equals(v)) {
			v.prev = find(v.prev);
		}
		// Return v's root.
		return v.prev;
	}
	
	public void union(Vertex srcRoot, Vertex destRoot) {
//		Vertex srcRoot = find(src);
//		Vertex destRoot = find(dest);
		
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
	
	/*
	 * In cycle detection, if two given connected vertices have the same root, 
	 * since the root also connects both of them, a triangle is formed, which 
	 * yields a cycle.
	 * 
	 * Note: union-find approach is not able to print all paths of cycles in the 
	 * undirected graph, because:
	 * 1. Each vertex in a set links directly to the root, no path info (path compression)
	 * 2. The paths are united by roots, rather than correct relative path vertices.
	 * 3. A cycle happens only when a new edge added, however if this edge cuts one
	 *    big cycle into two (property of undirected graph), it only reports 1 time,
	 *    rather than 2.
	 */
	public boolean cycle() {
//		int count = 0;
		for(Vertex src:G.adjList.keySet()) {
			for(Vertex dest:G.adjList.get(src)) {
				
				Vertex srcRoot = find(src);
				Vertex destRoot = find(dest);

				if(srcRoot.equals(destRoot)) {
//					count++;
					return true;
				}
				else {
					union(srcRoot, destRoot);
				}
			}
		}
//		return count;
		return false;
	}

	public static void main(String[] args) {
		Graph g = new Graph();
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);
		Vertex v5 = new Vertex(5);
		
		// Though union-find algorithm only works for undirected graph,
		// we use directed graph structure to avoid duplicated double directed
		// edges in undirected graph.
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
