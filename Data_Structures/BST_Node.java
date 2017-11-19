package data_structures;

public class BST_Node {

	int data;
	BST_Node left;
	BST_Node right;
	
	public BST_Node(int data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}
	
	public BST_Node(int data, BST_Node left, BST_Node right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

}
