package graph;

import java.util.HashMap;
import java.util.Map;

/*
 * This data structure may be used by Dijkstra's and Prim's algorithms.
 */
public class BinaryMinHeap {
	int capacity;
	int size;
	Vertex[] arr;
	Map<Vertex, Integer> map;

	public BinaryMinHeap(int capacity) {
		this.size = 0;
		this.capacity = capacity;
		this.arr = new Vertex[capacity];
		this.map = new HashMap<>();
	}
	
	public void insert(Vertex v) {
		if (this.size >= this.capacity) {
			System.out.println("Overflow: failed to insert.");
			return;
		}
		int i = this.size;       //initial index of inserted key, aka the last position of current array
		this.arr[i] = v;
		this.size++;
		map.put(v, i);
		while (i != 0 && this.arr[(i - 1) / 2].cost > this.arr[i].cost) { //compare inserted node with its parent, if swap and go up, keep doing because it may be very very large till top
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}
	
	public Vertex extractMin() {
		if (this.size <= 0) {
			System.out.println("Underflow: heap length is 0.");
			return null;
		}
		Vertex min = this.arr[0];
		map.remove(min);
		this.arr[0] = this.arr[this.size - 1]; //just like insert, after remove, insert the last node to the 1st, then check if qualified
        map.put(this.arr[0], 0);
		this.size--;
		MinHeapify(0);
		return min;
	}
	
	public void MinHeapify(int i) {
		int l = 2 * i + 1; //left child of i
		int r = 2 * i + 2; //right child of i
		int min = i;
		//ensure the index of children is less than current size, otherwise it's invalid node
		//heap.length() = size - 1, so max(l) = size - 1, not size
		if (l < this.size && this.arr[l].cost < this.arr[min].cost) {
			min = l;
		}
		if (r < this.size && this.arr[r].cost < this.arr[min].cost) {
			min = r;
		}
		if (min != i) {
			swap(min, i); //now arr[i] is min, arr[min] > arr[i], the other child is already < arr[i]
			MinHeapify(min); //since the other child is already heapified, we only need to check the changed child
		}
	}
	
	// After decrease, the vertex at i may has value less than its parent, so need to
	// go up and check.
	public void decrease(int i, int value) {
		this.arr[i].cost = value;
		while (i != 0 && this.arr[(i - 1) / 2].cost > this.arr[i].cost) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}
	
	public boolean contains(Vertex v) {
		return map.containsKey(v);
	}
	
	public void swap(int m, int n) {
		Vertex temp = this.arr[m];
		this.arr[m] = this.arr[n];
		this.arr[n] = temp;
		map.put(this.arr[m], m);
		map.put(this.arr[n], n);
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}

	public static void main(String[] args) {
		Vertex v0 = new Vertex(0);
		v0.cost = -5;
		Vertex v1 = new Vertex(1);
		v1.cost = 10;
		Vertex v2 = new Vertex(2);
		v2.cost = 115;
		Vertex v3 = new Vertex(3);
		v3.cost = -98;
		Vertex v4 = new Vertex(4);
		v4.cost = 34;
		Vertex v5 = new Vertex(5);
		v5.cost = -20;
		
		BinaryMinHeap h = new BinaryMinHeap(6);
		h.insert(v0);
		h.insert(v1);
		h.insert(v2);
		h.insert(v3);
		h.insert(v4);
		h.insert(v5);
		h.decrease(4, -1000);
//		h.extractMin();
		System.out.println(h.contains(v4));
		System.out.println(h.size);
		for(Vertex v:h.arr) {
			System.out.print("Vertex: " + v.value + " Cost: " + v.cost);
			System.out.println(" Position: " + h.map.get(v));
		}
	}

}
