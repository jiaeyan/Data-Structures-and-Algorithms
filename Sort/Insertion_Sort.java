package sort;

/*
 * [sorted list | x, unsorted list], 
 * sorted list = [ >x | ... | <x], replace x with ...
 * Apply reversed bubble sort inside sorted list.
 * Time complexity: best O(n), already all sorted; worst O(n^2), all reversed; average O(n^2), stable.
 */

public class Insertion_Sort {

	public static void main(String[] args) {
		int[] arr = {4,2,5,7,9,8,3,1,6,0};
		Insertion_Sort is = new Insertion_Sort();
		
		int[] res1 = is.swap_sort(arr);
		int[] res2 = is.skip_sort(arr);
		
		System.out.println("Swap Sort:");
		for (int i = 0; i < res1.length; i++) System.out.println(res1[i]);
		System.out.println("Skip Sort:");
		for (int i = 0; i < res2.length; i++) System.out.println(res2[i]);
	}
	
	//Given a sublist, bubble sort (one pass is enough because of former sortings, so no flag needed).
	public int[] swap_sort(int[] arr) {
		for (int i = 1; i < arr.length; i++) { //start from 1 rather 0 because no need to compare 1st itself
			for (int j = i; j > 0; j--) {      //till length rather than length-1 because the last element also participates in insertion
				if (arr[j] < arr[j-1]) {
					int temp = arr[j];
					arr[j] = arr[j-1];
					arr[j-1] = temp;
				}
			}
		}
		return arr;
	}
	
	//Only compare target with all sorted sublist until get its position.
	public int[] skip_sort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			int key = arr[i];
			int j = i - 1;
			while (j >= 0 && arr[j] > key) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = key;
		}

//		for (int i = 1; i < arr.length; i++) {
//			int key = arr[i];
//			int index = 0;
//			for (int j = i - 1; j >= 0; j--) {
//				if (arr[j] > key) {
//					arr[j+1] = arr[j];
//					index = j;
//				}
//				else break;
//			arr[index] = key;
//			}
//		}
		return arr;
	}

}
