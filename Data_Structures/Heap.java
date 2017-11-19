package data_structures;

/*
 * This is a max heap. Min heap is the same idea.
 * Arr[0]       Returns the root node
 * Arr[(i-1)/2]	Returns the parent node
 * Arr[(2*i)+1]	Returns the left child node
 * Arr[(2*i)+2]	Returns the right child node
 * Traverse in level order
 * insert(): go up and check with parent
 * heapify(): go down and check with children
 */

public class Heap {
	private int size;
	private int capacity;
	private int[] arr;

	public static void main(String[] args) {
		Heap h = new Heap(10);
//		int[] arr = {2,4,3,8,1,0,5,7,6,9};
//		h.buildMaxHeap(arr);
		h.insert(2);
		h.insert(4);
		h.insert(5);
		h.insert(3);
		h.insert(1);
		h.insert(6);
//		h.delete(4);
		for (int i = 0; i < h.size; i++) System.out.print(" " + h.arr[i]);
		System.out.println();
		h.delete(4);

		for (int i = 0; i < h.size; i++) System.out.print(" " + h.arr[i]);
	}
	
	public Heap(int capacity) {
		this.size = 0;
		this.capacity = capacity;
		this.arr = new int[capacity];
	}
	
	//Always insert at the last of the array, then check its parent and compare,
	//swap if possible, until reach the top.
	public void insert(int k) {
		if (this.size >= this.capacity) {
			System.out.println("Overflow: failed to insert.");
			return;
		}
		int i = this.size;       //initial index of inserted key, aka the last position of current array
		this.arr[this.size] = k;
		this.size ++;
		while (i != 0 && this.arr[(i - 1) / 2] < this.arr[i]) { //compare inserted node with its parent, if swap and go up, keep doing because it may be very very large till top
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}
	
	public int extractMax() {
		if (this.size <= 0) {
			System.out.println("Underflow: heap length is 0.");
			return Integer.MIN_VALUE;
		}
		int max = this.arr[0];
		this.arr[0] = this.arr[this.size - 1]; //just like insert, after remove, insert the last node to the 1st, then check if qualified
		this.size --;
		MaxHeapify(0);
		return max;
	}
	
	public int getMax() {
		return this.arr[0];
	}
	
	//increase the value of certain node, after increase, check with parent to see if it becomes bigger than parent; just like insert
	public void increase(int i, int value) {
		if (value < 0) {
			System.out.println("Error: the value should >= 0.");
			return;
		}
		this.arr[i] += value;
		while (i != 0 && this.arr[(i - 1) / 2] < this.arr[i]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		} 
	}
	
	//decrease the value of certain node, after decrease, check it with its children to see if it becomes smaller than children; just like maxheapify
	public void decrease(int i, int value) {
		if (value < 0) {
			System.out.println("Error: the value should >= 0.");
			return;
		}
		this.arr[i] -= value;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int max = i;
		if (l < this.size && this.arr[l] > this.arr[i]) {
			max = l;
		}
		if (r < this.size && this.arr[r] > this.arr[max]) {
			max = r;
		}
		if (max != i) {
			swap(max, i);
			MaxHeapify(max);
		} 
	}
	
	//delete given position node. First make it to the top, then extract it, so the heap will still be balanced.
	public void delete(int i) {
		increase(i, this.arr[0]);
		extractMax();
	}
	
	public void buildMaxHeap(int[] arr) {
		//Start from size/2 are leaf nodes, so size/2 -1 is the last node that has children.
		//Since heapify only happens between parent and children, we should start from the last node that has children.
		if (arr.length > this.capacity) {
			System.out.println("Overflow: distinct lengths.");
			return;
		}
		this.arr = arr;
		this.size = arr.length;
		for (int i = this.size / 2 - 1; i >= 0; i--) {
			MaxHeapify(i);
		}
	}
	//Given a node, compare with its children to check if it is qualified to be its position
	//Assume all subtrees are already heapified.
	public void MaxHeapify(int i) {
		int l = 2 * i + 1; //left child of i
		int r = 2 * i + 2; //right child of i
		int max = i;
		//ensure the index of children is less than current size, otherwise it's invalid node
		//heap.length() = size - 1, so max(l) = size - 1, not size
		if (l < this.size && this.arr[l] > this.arr[i]) {
			max = l;
		}
		if (r < this.size && this.arr[r] > this.arr[max]) {
			max = r;
		}
		if (max != i) {
			swap(max, i); //now arr[i] is max, arr[max] < arr[i], the other child is already < arr[i]
			MaxHeapify(max); //since the other child is already heapified, we only need to check the changed child
		}
	}
	
	//A help function to swap values of the array.
	public void swap(int m, int n) {
		int temp = this.arr[m];
		this.arr[m] = this.arr[n];
		this.arr[n] = temp;
	}
}
