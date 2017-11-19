package data_structures;

/*
 * This is a max heap. Min heap is the same idea.
 * Arr[0] returns the root node
 * Arr[i/2]	Returns the parent node
 * Arr[(2*i)+1]	Returns the left child node
 * Arr[(2*i)+2]	Returns the right child node
 * Traverse in level order
 */

public class Heap {
	private int size;
	private int capacity;
	private int[] arr;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Heap(int capacity) {
		this.size = 0;
		this.capacity = capacity;
		this.arr = new int[capacity];
	}
	
	//given a node, compare with its children to check if it is qualified to be its position
	public void heapify(int i) {
		int l = 2 * i + 1; //left child of i
		int r = 2 * i + 2; //right child of i
		        //heap.length() = size - 1, so max(l) = size - 1, l != size
		if (l < this.size && this.arr[l] >= this.arr[i]) {
			
		}
	}
	
	//A help function to swap values of the array.
	public void swap(int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
