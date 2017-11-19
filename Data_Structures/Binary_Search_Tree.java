package data_structures;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

/*
 * A representation of binary search tree (BST), with search, insert, delete,
 * DFS traversal of pre-order, in-order, post-order and BFS traversal of level-order.
 */

/*
 * BFS - visit all children, then grandchildren, then great-grandchildren, etc..
 * DFS - visit a child, then that child's child and so forth, 
 * when you reach as far as you can go via a child, x,  
 * you move back up a level and continue the process with the sibling of x (call it y),
 * includes pre-order, in-order and post-order.
 */

public class Binary_Search_Tree {
	
	public static void main(String[] args) {
		Binary_Search_Tree bst = new Binary_Search_Tree();
		BST_Node root = new BST_Node(8, new BST_Node(3, new BST_Node(1), new BST_Node(6, new BST_Node(4), new BST_Node(7))), new BST_Node(10, null, new BST_Node(14, new BST_Node(13), null)));
//		System.out.println(bst.search_iterative(root, 6));
//		System.out.println(bst.search_iterative(root, 4));
		bst.insert_iterative(root, 15);
//		bst.preorder_DFS(root);
//		System.out.println(bst.search_iterative(root, 3));
//		bst.preorder(root);
//		System.out.println();
		bst.levelorder_BFS(root);
//		System.out.println(bst.getHeight(root));
		bst.levelorder_recursive(root);
//		System.out.println();
//		bst.postorder(root);
	}
	
	public BST_Node search_recursive(BST_Node root, int key) {
		if (root == null || key == root.data) {return root;}
		if (key > root.data) {return search_recursive(root.right, key);}
		if (key < root.data) {return search_recursive(root.left, key);}
		return root;
	}
	
	//A new key is always inserted at leaf. 
	public BST_Node insert_recursive(BST_Node root, int key) {
		if (root == null) {root = new BST_Node(key);}
		else {                    //add node to either branch, if "{return insert_recursive()}", only returns the child at last
			if (key > root.data) {root.right = insert_recursive(root.right, key);}
			else if (key < root.data) {root.left = insert_recursive(root.left, key);}
		    //if (key == root.data) {do nothing;}
		}
		return root;
	}
	
	public BST_Node delete_recursive(BST_Node root, int key) {return root;}
	
	public void preorder_recursive(BST_Node root){
		if(root != null){
			System.out.print(" " + root.data);
			preorder_recursive(root.left);
			preorder_recursive(root.right);
		}
	}
	
	//Retrieves data in sorted order.
	public void inorder_recursive(BST_Node root) {
		if(root != null){
			inorder_recursive(root.left);
			System.out.print(" " + root.data);
			inorder_recursive(root.right);
		}
	}
	
	public void postorder_recursive(BST_Node root){
		if(root != null){
			postorder_recursive(root.left);
			postorder_recursive(root.right);
			System.out.print(" " + root.data);
		}
	}
	
	public void levelorder_recursive(BST_Node root) {
		int h = getHeight(root);
        for (int i = 1; i <= h; i++) {levelNodes(root, i);}
	}
	
	public void levelNodes(BST_Node root, int level) {
		if (root == null) {return;}
	    if (level == 1) {System.out.print(" " + root.data);}
	    else if (level > 1) {
	    	    levelNodes(root.left, level - 1);
	    	    levelNodes(root.right, level - 1);
	    }
	}
	
	public int getHeight(BST_Node root) {
		if (root == null) {return 0;}
		int leftHeight = getHeight(root.left);
		int rightHeight = getHeight(root.right);
		return Math.max(leftHeight, rightHeight) + 1;
	}
	
	public BST_Node search_iterative(BST_Node root, int key) {
		BST_Node curr = root;
		while (curr != null) {
			if (key == curr.data) {return curr;}
			if (key > curr.data) {curr = curr.right;}
			else {curr = curr.left;}
		}
		return curr;
	}
	
	public BST_Node insert_iterative(BST_Node root, int key) {
		if (root == null) {root = new BST_Node(key);}
		else {
			BST_Node parent = null;
			BST_Node curr = root;
			// find the appropriate leaf?? (doesn't have to be) to insert node
			while (curr != null) {
				parent = curr;
				if (key > curr.data) {curr = curr.right;}
				else if (key < curr.data) {curr = curr.left;}
				else {return root;}
			}
			if (key > parent.data) {parent.right = new BST_Node(key);}
			else {parent.left = new BST_Node(key);}
		}
		return root;
	}
	
	public BST_Node delete_iterative(BST_Node root, int key) {return root;}
	
	public void preorder_DFS(BST_Node root) {
		if (root == null) {return;}
		Stack<BST_Node> st = new Stack<>();
		st.push(root);
		while (!st.isEmpty()) {
			BST_Node node = st.pop();
			System.out.print(" " + node.data);
			//right child is pushed first so that left is processed first
			if (node.right != null) {st.push(node.right);}
			if (node.left != null) {st.push(node.left);}
		}
	}
	
	public void inorder_DFS(BST_Node root) {
		Stack<BST_Node> st = new Stack<>();
		while (!st.isEmpty() || root != null) {
			if (root != null) {
				st.push(root);
				root = root.left;
			}
			else {
				BST_Node node = st.pop();
				System.out.print(" " + node.data);
				root = node.right;
			}
		}
	}
	
	public void postorder_DFS(BST_Node root) {
		Stack<BST_Node> st = new Stack<>();
		BST_Node lastNodeVisited = null;
		while (!st.isEmpty() || root != null) {
			if (root != null) {
				st.push(root);
				root = root.left;
			}
			else {
				BST_Node peekNode = st.peek();
				// if right child exists and traversing node
			    // from left child, then move right
			     if (peekNode.right != null && lastNodeVisited != peekNode.right) {
			    	     root = peekNode.right;
			     }
			     else {
			    	     System.out.print(" " + peekNode.data);
				     lastNodeVisited = st.pop();
			     }   
			}
		}
	}
	
	public void levelorder_BFS(BST_Node root) {
		if (root == null) {return;}
		Queue<BST_Node> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			BST_Node node = q.remove();
			System.out.print(" " + node.data);
		    if (node.left != null) {q.add(node.left);}
		    if (node.right != null) {q.add(node.right);}
		}	    
	}
}
