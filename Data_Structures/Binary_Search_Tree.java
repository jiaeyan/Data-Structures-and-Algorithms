package data_structures;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

/*
 * A representation of binary search tree (BST), with search, insert, delete,
 * pre-order, in-order and post-order traversal methods.
 */

public class Binary_Search_Tree {
	
	public static void main(String[] args) {
		Binary_Search_Tree bst = new Binary_Search_Tree();
		Tree_Node root = new Tree_Node(8, new Tree_Node(3, new Tree_Node(1), new Tree_Node(6, new Tree_Node(4), new Tree_Node(7))), new Tree_Node(10, null, new Tree_Node(14, new Tree_Node(13), null)));
//		System.out.println(bst.search_iterative(root, 6));
//		System.out.println(bst.search_iterative(root, 4));
		bst.insert_iterative(root, 15);
		bst.preorder_DFS(root);
//		System.out.println(bst.search_iterative(root, 3));
//		bst.preorder(root);
//		System.out.println();
		bst.preorder_BFS(root);
//		System.out.println();
//		bst.postorder(root);
	}
	
	public Tree_Node search_recursive(Tree_Node root, int key) {
		if (root == null || key == root.data) {return root;}
		if (key > root.data) {return search_recursive(root.right, key);}
		if (key < root.data) {return search_recursive(root.left, key);}
		return root;
	}
	
	//A new key is always inserted at leaf. 
	public Tree_Node insert_recursive(Tree_Node root, int key) {
		if (root == null) {root = new Tree_Node(key);}
		else {                    //add node to either branch, if "{return insert_recursive()}", only returns the child at last
			if (key > root.data) {root.right = insert_recursive(root.right, key);}
			else if (key < root.data) {root.left = insert_recursive(root.left, key);}
		    //if (key == root.data) {do nothing;}
		}
		return root;
	}
	
	public Tree_Node delete_recursive(Tree_Node root, int key) {return root;}
	
	public void preorder_recursive(Tree_Node root){
		if(root != null){
			System.out.print(" " + root.data);
			preorder_recursive(root.left);
			preorder_recursive(root.right);
		}
	}
	
	//Retrieves data in sorted order.
	public void inorder_recursive(Tree_Node root) {
		if(root != null){
			inorder_recursive(root.left);
			System.out.print(" " + root.data);
			inorder_recursive(root.right);
		}
	}
	
	public void postorder_recursive(Tree_Node root){
		if(root != null){
			postorder_recursive(root.left);
			postorder_recursive(root.right);
			System.out.print(" " + root.data);
		}
	}
	
	public Tree_Node search_iterative(Tree_Node root, int key) {
		Tree_Node curr = root;
		while (curr != null) {
			if (key == curr.data) {return curr;}
			if (key > curr.data) {curr = curr.right;}
			else {curr = curr.left;}
		}
		return curr;
	}
	
	public Tree_Node insert_iterative(Tree_Node root, int key) {
		if (root == null) {root = new Tree_Node(key);}
		else {
			Tree_Node parent = null;
			Tree_Node curr = root;
			// find the appropriate leaf?? (doesn't have to be) to insert node
			while (curr != null) {
				parent = curr;
				if (key > curr.data) {curr = curr.right;}
				else if (key < curr.data) {curr = curr.left;}
				else {return root;}
			}
			if (key > parent.data) {parent.right = new Tree_Node(key);}
			else {parent.left = new Tree_Node(key);}
		}
		return root;
	}
	
	public void preorder_DFS(Tree_Node root) {
		if (root == null) {return;}
		Stack<Tree_Node> st = new Stack<>();
		st.push(root);
		while (!st.isEmpty()) {
			Tree_Node node = st.pop();
			System.out.print(" " + node.data);
			//right child is pushed first so that left is processed first
			if (node.right != null) {st.push(node.right);}
			if (node.left != null) {st.push(node.left);}
		}
	}
	
	public void inorder_DFS(Tree_Node root) {
		Stack<Tree_Node> st = new Stack<>();
		while (!st.isEmpty() || root != null) {
			if (root != null) {
				st.push(root);
				root = root.left;
			}
			else {
				Tree_Node node = st.pop();
				System.out.print(" " + node.data);
				root = node.right;
			}
		}
	}
	
	public void postorder_DFS(Tree_Node root) {
		Stack<Tree_Node> st = new Stack<>();
		Tree_Node lastNodeVisited = null;
		while (!st.isEmpty() || root != null) {
			if (root != null) {
				st.push(root);
				root = root.left;
			}
			else {
				Tree_Node peekNode = st.peek();
				// if right child exists and traversing node
			    // from left child, then move right
			     if (peekNode.right != null && lastNodeVisited != peekNode.right) {
			    	     root  = peekNode.right;
			     }
			     else {
			    	     System.out.print(" " + peekNode.data);
				     lastNodeVisited = st.pop();
			     }   
			}
		}
	}
	
	public void preorder_BFS(Tree_Node root) {
		if (root == null) {return;}
		Queue<Tree_Node> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			Tree_Node node = q.remove();
			System.out.print(" " + node.data);
		    if (node.left != null) {q.add(node.left);}
		    if (node.right != null) {q.add(node.right);}
		}
		    
	}
}
