package string;

import java.util.Arrays;

/*
 * A class to perform least significant digit radix sorting.
 * The processing of the keys begins at the least significant digit
 * (i.e., the rightmost digit), and proceeds to the most significant
 * digit.
 * 
 * NOTE: this algorithm only allows items all at the same length.
 */
public class LSDRadixSort {

	public LSDRadixSort() {
		// TODO Auto-generated constructor stub
	}
	
	// The input remains untouched, the output array will be modified
	// at each pass.
	public String[] sort(String[] input) {
		int len = input.length;
		int w = input[0].length();
		String[] output = Arrays.copyOf(input, len);
		
		// Start from last/least significant digit/char.
		for(int d = w - 1; d >= 0; d--) {
			int[] count = new int[256];
			String[] temp = new String[len];
			
			for(int i = 0; i < len; i++) {
				count[output[i].charAt(d)]++;
			}
			
			for(int i = 1; i < 256; i++) {
				count[i] += count[i-1];
			}
			
			for(int i = len - 1; i >= 0; i--) {
				temp[count[output[i].charAt(d)]-1] = output[i];
				count[output[i].charAt(d)]--;
			}
			
			// Modify the sorted output array.
			System.arraycopy(temp, 0, output, 0, len);
		}
		
		return output;
	}

	public static void main(String[] args) {
		LSDRadixSort lsd = new LSDRadixSort();
		String[] arr = {"cat", "eye", "cut", "arc", "bye", "but", "cat"};
		for(String str:lsd.sort(arr)) {
			System.out.println(str);
		}
	}

}
