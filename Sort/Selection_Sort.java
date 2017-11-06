
/*
 * Search entire list to get minimum, swap to head to form sorted sublist,
 * do the same for the rest unsorted sublist. Happen in place, no new list needed.
 * Time complexity: best O(n), worst = average = O(n^2), stable. 
 */

public class Selection_Sort {

	public static void main(String[] args) {
		int[] arr = {4,2,5,7,9,8,3,1,6,0};
		Selection_Sort ss = new Selection_Sort();
		ss.sort(arr);
		for (int i = 0; i < arr.length; i++) System.out.println(arr[i]);

	}
	
	public void sort(int[] arr) {
		//start point of each loop, each iteration increases 1 because in last iteration we found the minimum one and put it in the sorted list, whose length += 1
		for (int i = 1; i < arr.length; i++) {
			//always assume minimum item is the first item at first in each iteration
			int min = i;
			//go through the whole unsorted sublist and find out the sublist's minimum item's index
			for (int j = i; j < arr.length; j++) {
				if (arr[j] < arr[min]) {
					min = j;
				}
			}
			//swap values if the last element in sorted list is < min
			if (arr[i-1] > arr[min]) {
				int temp = arr[i-1];
				arr[i-1] = arr[min];
				arr[min] = temp;
			}
		}
	}

}
