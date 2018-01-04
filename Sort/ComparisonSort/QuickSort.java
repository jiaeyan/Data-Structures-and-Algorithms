package sort;

/*
 * Fix a pivot, put all elements less than it on the left and higher on the right,
 * thus the position of pivot is fixed, then cut the list into two sublists according
 * to the pivot index (exclusive) and do the same until the sublist only has 1 element.
 * Only need to swap values of items in place, so no need to return new list.
 * Time complexity: best = average = nlogn, worst = n^2, not stable.
 */

public class QuickSort {

	public static void main(String[] args) {
		int[] arr = {4,11,2,10,5,7,9,8,5,3,1,6,0};
		QuickSort qs = new QuickSort();
		qs.sort(arr, 0, arr.length-1);
		for (int i = 0; i < arr.length; i++) System.out.println(arr[i]);
	}
	
	public void sort(int[] arr, int start, int end) {
		//base case: if only one element in list, no need to partition more
		if (start < end) {
            //get partitioning index (pi), cut the list into two parts by it
            int pi = lomutoPartition(arr, start, end);
//            int pi = hoarePartition(arr, start, end);
            //the position of item on partition index is known, no need to include it again, so it's pi-1 rather than pi
            sort(arr, start, pi-1);
            //same reason as above, use pi+1 instead of pi
            sort(arr, pi+1, end);
        }
	}
	
	/*
	 * A traditional partition in a linear way.
	 */
	public int lomutoPartition(int[] arr, int start, int end) {
		//make the last element as pivot, now we know the value of pivot, we have to find the position it should be
		int pivot = arr[end];
		//the 2nd sublist doesn't start from 0, so use start instead
		//the goal is to ensure elements on [start, pi) are all < pivot, [pi, end] > pivot 
		int pi = start;
		//this loop doesn't reach end (end-1 instead) because end is taken as pivot
		//go through the list, if encounter one element less than pivot, swap it (mostly latter part of the list) with item on pi, to ensure smaller ones are at the leftmost
		for (int i = start; i < end; i++) {
			if (arr[i] < pivot) {
				//if arr[i] is less thant pivot, swap their values
				//after swap the lower value is at pi, then pi increments, so the item on pi+1 is higher than pivot,
				int temp = arr[i];
				arr[i] = arr[pi];
				arr[pi] = temp;
				//since found one smaller item on leftmost, the position of pivot should move right by 1
				pi++;
			}
		}
		//since only [start, pi) are < pivot, item on pi > pivot or not sure,
		//we can safely swap the pivot with the value on pi
		if (arr[pi] > arr[end]) {
			int temp = arr[end];
			arr[end] = arr[pi];
			arr[pi] = temp;
		}
		return pi;
	}
	
	/*
	 * Two pointers applied in partition, faster than linear partition.
	 */
	public int hoarePartition(int[] arr, int start, int end) {
		int pivot = arr[end];
		int left = start;
		int right = end - 1;
		boolean done = false;
		// when left encounters a value > pivot and right encouters < pivot, swap those
		// to ensure the smaller ones on left and bigger ones on right
		// put left <= right first because sometimes arr[left/right] may cause "index out of range" error
		while (!done) {
			while (left <= right && arr[left] < pivot) {left++;}
			while (left <= right && arr[right] > pivot) {right--;}
			if (left >= right) {done = true;}
			else {
				int temp = arr[right];
				arr[right] = arr[left];
				arr[left] = temp;
			}
		}
		// things before left are all < pivot, so make left as partition index
		if (arr[left] > arr[end]) {
			int temp = arr[end];
			arr[end] = arr[left];
			arr[left] = temp;
		}
		return left;
	}
}
