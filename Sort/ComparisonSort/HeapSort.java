package sort;

public class HeapSort {

	public static void main(String[] args) {
		int[] arr = {4,11,2,10,5,7,9,8,5,2,3,1,6,0};
		HeapSort hs = new HeapSort();
		hs.sort(arr);
		for (int i = 0; i < arr.length; i++) System.out.println(arr[i]);
	}
	
	public void sort(int[] arr) {
		int len = arr.length;
		buildMaxHeap(arr, len);
		// One by one extract an element from heap
        for (int i = len - 1; i >= 0; i--) {
            // Move current root to end
            swap(arr, 0, i);
            // call max heapify on the reduced heap, the last position will never be touched, aka biggest
            MaxHeapify(arr, i, 0);
        }
	}
	
	public void buildMaxHeap(int[] arr, int len) {
		//Start from size/2 are leaf nodes, so size/2 -1 is the last node that has children.
		//Since heapify only happens between parent and children, we should start from the last node that has children.
		for (int i = len / 2 - 1; i >= 0; i--) {
			MaxHeapify(arr, len, i);
		}
	}
	
	//Given a node, compare with its children to check if it is qualified to be its position
	//Assume all subtrees are already heapified.
	public void MaxHeapify(int[] arr, int len, int i) {
		int l = 2 * i + 1; //left child of i
		int r = 2 * i + 2; //right child of i
		int max = i;
		//ensure the index of children is less than current size, otherwise it's invalid node
		//heap.length() = size - 1, so max(l) = size - 1, not size
		if (l < len && arr[l] > arr[i]) {
			max = l;
		}
		if (r < len && arr[r] > arr[max]) {
			max = r;
		}
		if (max != i) {
			swap(arr, max, i);         //now arr[i] is max, arr[max] < arr[i], the other child is already < arr[i]
			MaxHeapify(arr, len, max); //since the other child is already heapified, we only need to check the changed child
		}
	}
	
	//A help function to swap values of the array.
	public void swap(int[] arr, int m, int n) {
		int temp = arr[m];
		arr[m] = arr[n];
		arr[n] = temp;
	}

}
