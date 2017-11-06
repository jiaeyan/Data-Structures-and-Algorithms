package search;

/*
 * Find the middle item in the list and compare with target, 
 * if target is smaller, find in left part, otherwise in right part.
 * Time complexity: log(n).
 */

public class Binary_Search {

	public static void main(String[] args) {
		int[] arr = {1,2,3,4,5,5,6,7,8,9,10}; // the list should be sorted in advance
		Binary_Search bs = new Binary_Search();
//		System.out.println(bs.recursiveSearch(arr, 1, 0, arr.length-1)); // since we use index here, it should be reachable, so use length-1 rather than length
		System.out.println(bs.iterativeSearch(arr, 11, 0, arr.length-1));
	}
	
	public int recursiveSearch(int[] arr, int target, int l, int h) {
		if (l > h) {return -1;}
		int mid = (l + h) / 2; // mid = l + (h - l) / 2 = (h + l) / 2
		if (target < arr[mid]) {return recursiveSearch(arr, target, l, mid - 1);} // arr[mid] is no use anymore, get rid of it in both parts
		else if (target > arr[mid]) {return recursiveSearch(arr, target, mid + 1, h);}
		else {return mid;}
	}
	
	public int iterativeSearch(int[] arr, int target, int l, int h) {
		while (true) {
			if (l > h) {return -1;}
			int mid = (l + h) / 2;
			if (target < arr[mid]) {h = mid - 1;}
			else if (target > arr[mid]) {l = mid + 1;}
			else {return mid;}
		}
	}

}
