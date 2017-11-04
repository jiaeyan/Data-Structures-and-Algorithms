
/*
 * Compare two neighboring elements each time, swap if 1st > 2nd.
 * Algorithm doesn't know when to stop, so set a flag.
 * If the flag indicates no swap from last pass, the array is sorted, stop.
 * Time complexity: best O(n), worst O(n^2), average O(n^2), stable.
 */

public class Bubble_Sort {

	public static void main(String[] args) {
		int[] arr = {4,2,5,7,9,8,3,1,6,0};
		Bubble_Sort bs = new Bubble_Sort();
		
		int[] res1 = bs.naive_sort(arr);
		int[] res2 = bs.shrink_sort(arr);
		int[] res3 = bs.skip_sort(arr);
		
		System.out.println("Naive Sort:");
		for (int i = 0; i < res1.length; i++) System.out.println(res1[i]);
		System.out.println("Shrink Sort:");
		for (int i = 0; i < res2.length; i++) System.out.println(res2[i]);
		System.out.println("Skip Sort:");
		for (int i = 0; i < res3.length; i++) System.out.println(res3[i]);
	}
	
	//When final pass is done, still need one whole pass to confirm no swapped happens.
	public int[] naive_sort(int[] arr) {
		boolean swapped = true; //a flag to indicate if swapped happened in last pass
		while (swapped) {
			swapped = false;
			for (int i = 0; i < arr.length - 1; i++) {
				if (arr[i] > arr[i+1]) {
					int temp = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = temp;
					swapped = true;
				}
			}
		}
		return arr;
	}
	
	//Skip the last element in current pass because that one is the biggest in the past pass.
	public int[] shrink_sort(int[] arr) {
		boolean swapped = true;
		int num = arr.length;
		while (swapped) {
			swapped = false;
			for (int i = 0; i < num - 1; i++) {
				if (arr[i] > arr[i+1]) {
					int temp = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = temp;
					swapped = true;
				}
			}
			num--; //shrink the range from end to head
		}
		return arr;
	}
	
	//When swap happens, set flag there, next time only need to run until that flag.
	public int[] skip_sort(int[] arr) {
		int len = arr.length;
		while (len != 0) {
			int flag = 0;
			for (int i = 0; i < len - 1; i++) {
				if (arr[i] > arr[i+1]) {
					int temp = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = temp;
					flag = i + 1; //no need to check arr[i+1] anymore, next run to i
				}
			}
			len = flag;
		}
		return arr;
	}

}
