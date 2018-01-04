package sort;

import java.util.Arrays;


/*
 * This is a sorting algorithm based on counting sorting.
 * Sort the input from least significant digit to most significant
 * digit.
 * 
 * Complexity:
 * - Time: p(n+k), n is the number of input items, k is the range,  
 *         for digits 0-9, p is the number of digits in max value of
 *         input.
 * - Space: O(n+k)
 */
public class RadixSort {

	public RadixSort() {
		// TODO Auto-generated constructor stub
	}
	
	public int[] sort(int[] input) {
		int len = input.length;
		int max = max(input);
		
		// This output array will be modified at each pass, and
		// returned as the result.
		int[] output = Arrays.copyOf(input, len);
		
		
		// Make as many passes as the number of digits of max to
		// ensure each item is iterated over all digits.
		for(int exp = 1; max/exp > 0; exp *= 10) {
			CountSort(output, exp, len);
		}
		
		return output;
	}
	
	public void CountSort(int[] output, int exp, int len) {
		int[] count = new int[10];
		int[] temp = new int[len];
		
		for(int i = 0; i < len; i++) {
			count[key(output[i], exp)]++;
		}
		
		for(int i = 1; i < 10; i++) {
			count[i] += count[i-1];
		}
		
		for(int i = len - 1; i >= 0; i--) {
			temp[count[key(output[i], exp)]-1] = output[i];
			count[key(output[i], exp)]--;
		}
		
		// Modify the output array at each pass to approach the result.
		System.arraycopy(temp, 0, output, 0, len);
	}
	
	public int max(int[] input) {
		int max = input[0];
		for(int item:input) {
			if(max < item) {
				max = item;
			}
		}
		return max;
	}
	
	// A function to convert a distinct item to a unique index.
	public int key(int item, int exp) {
		return (item / exp) % 10; 
	}

	public static void main(String[] args) {
		RadixSort rs = new RadixSort();
		int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
		for(int item:rs.sort(arr)) {
			System.out.println(item);
		}
		System.out.println();
		for(int item:arr) {
			System.out.println(item);
		}
	}
}