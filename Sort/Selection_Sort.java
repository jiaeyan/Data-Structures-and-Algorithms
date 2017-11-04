package sort;

/*
 * Search entire list to get minimum, swap to head to form sorted sublist,
 * do the same for the rest unsorted sublist.
 * Time complexity: best O(n), worst = average = O(n^2), stable. 
 */

public class Selection_Sort {

	public static void main(String[] args) {
		int[] arr = {4,2,5,7,9,8,3,1,6,0};
		Selection_Sort ss = new Selection_Sort();
		int[] res = ss.sort(arr);
		for (int i = 0; i < res.length; i++) System.out.println(res[i]);

	}
	
	public int[] sort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {      //start point of each loop
			int min = i;
			for (int j = i; j < arr.length; j++) {  //go through the whole unsorted sublist
				if (arr[j] < arr[min]) {
					min = j;
				}
			}
			if (arr[i-1] > arr[min]) {
				int temp = arr[i-1];
				arr[i-1] = arr[min];
				arr[min] = temp;
			}
		}
		return arr;
	}

}
