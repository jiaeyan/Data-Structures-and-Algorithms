package sort;

/*
 * Key/Integer sorting, rather than a comparison sorting.
 * 
 * This is a counting algorithm: convert items into indexes
 * continuously and ascendingly, each index represents a unique item.
 * 
 * Then count the frequency of each item/index, accumulate over, so
 * we know till the kth index/item, how many items should be ahead of
 * it (it should be count[k-1]).
 * 
 * Therefore the values in count array are the actual next positions of
 * corresponding item/index at the output array.
 * 
 * Stable.
 * 
 * Complexity:
 * Time: O(n+k), where n is the number of items and k is the range.
 * Space: O(n+k), n is for the output array while k is for the count array.
 */

public class CountingSort {
	
	public int[] sort(int[] input) {
		int len = input.length;
		
		// Step 0: iterate over the input to get the min and max values,
		//         then get the range. Note the number of items between
		//         the range should be max - min + 1, like 1 to 3 has 3,
		//         rather than 2 numbers.
		int[] args = maxmin(input);
		int max = args[0];
		int min = args[1];
		int range = max - min + 1;
		
		// Count array should be significantly smaller than input array.
		int[] count = new int[range];
		
		// Output array should be the same length as input array.
		int[] output = new int[len];
		
		// Step 1: count the frequency of each item in input.
		//         the key() function converts an item into a index
		//         of count array. Each distinct item has unique index.
		for(int i = 0; i < len; i++) {
			count[key(input[i], min)]++;
		}
		
		// Step 2: accumulate over the count array, by thus the value at
		//         each position indicates the next position of corresponding
		//         item in input array.
		for(int i = 1; i < range; i++) {
			count[i] += count[i-1];
		}
		
		// Step 3: get the last occurrence position from count array for each
		//         item in input, according to that position put that
		//         item in the output array.
		
		//         Then reduce the relative item's position value in
		//         count array by 1, meaning the next same valued item should
		//         be put ahead of the old one. 
		
		//         Since we iterate over the input reversely,
		//         we can ensure that the last occurrence of the same value also
		//         comes at the last of the output array.
		for(int i = len - 1; i >= 0 ; i--) {
			int pos = count[key(input[i], min)] - 1;
			output[pos] = input[i];
			count[key(input[i], min)]--;
		}
		
		return output;
	}
	
	// A helper function to get the max and min values from a input.
	public int[] maxmin(int[] input) {
		int[] res = new int[2];
		
		// This is the max value.
		res[0] = Integer.MIN_VALUE;
		
		// This is the min value.
		res[1] = Integer.MAX_VALUE;
		for(int i = 0; i < input.length; i++) {
			if(res[0] < input[i]) {
				res[0] = input[i];
			}
			if(res[1] > input[i]) {
				res[1] = input[i];
			}
		}
		return res;
	}
	
	// This is a helper function to convert the item into
	// an index at count array.
	// Since index of an array starts from 0, the min value
	// may be quite larger than 0, so we use item - min to
	// ensure the index starts from 0.
	public int key(int item, int min) {
		return item - min;
	}

	public static void main(String[] args) {
		CountingSort cs = new CountingSort();
		int[] input = new int[] {0,6,-3,7,2,1,5,3,3,6,-9,4,4};
		for(int item:cs.sort(input)) {
			System.out.println(item);
		}
	}

}
