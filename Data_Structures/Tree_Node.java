package data_structures;

public class Tree_Node {

	int data;
	Tree_Node left;
	Tree_Node right;
	
	public Tree_Node(int data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}
	
	public Tree_Node(int data, Tree_Node left, Tree_Node right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

}
