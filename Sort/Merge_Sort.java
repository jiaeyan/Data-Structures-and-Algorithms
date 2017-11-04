import java.util.Arrays;

/*
 * Keep dividing list into half and half until each sublist only has 1 element,
 * merge two parts pair by pair and eventually get the entire sorted list.
 * Time complexity: worst = average = O(nlogn)
 */

public class Merge_Sort {

	public static void main(String[] args) {
		int[] arr = {4,2,10,5,7,9,8,3,1,6,0};
		Merge_Sort ms = new Merge_Sort();
		int[] res = ms.topDownSort(arr);
		for (int i = 0; i < res.length; i++) System.out.println(res[i]);
	}
	
	//Divide the list into 2 parts where each part is sorted.
	public int[] topDownSort(int[] arr) {
		if (arr.length == 1) return arr;
		int mid = arr.length/2;
		int[] left = topDownSort(Arrays.copyOfRange(arr, 0, mid));
		int[] right = topDownSort(Arrays.copyOfRange(arr, mid, arr.length));
		return merge(left, right);
	}
	
	public int[] bottomUpSort(int[] arr) {
		return arr;
	}
	
	//Merge two lists. Compare the 1st elements, push the smaller one into
	//auxiliary list, and move the relative list pointer forward.
	public int[] merge(int[] left, int[] right) {
		int[] result = new int[left.length + right.length];
		int l = 0, r = 0, k = 0;
		while (l < left.length && r < right.length) {
			if (left[l] < right[r]) {result[k++] = left[l++];}
			else {result[k++] = right[r++];}
		}
//		if (l<left.length) {result[k] = left[l];}        //one list may run out while another is still complete
//		else if (r<right.length) {result[k] = right[r];} //so purely get the last one is not enough
		while (l < left.length) {result[k++] = left[l++];}
		while (r < right.length) {result[k++] = right[r++];}
		return result;
	}

}
