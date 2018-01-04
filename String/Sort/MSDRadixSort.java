package string;

import java.util.Arrays;

/*
 * A class to perform most significant digit radix sorting.
 * The processing of the keys begins at the most significant digit
 * (i.e., the leftmost digit), and proceeds to the least significant
 * digit.
 * 
 * NOTE: since perform from 1st char, need to fix the 'a's before 'b's
 *       in any later pass, (LSD changes all items' positions at each
 *       pass), so only sort subarrays that start with the same char/prefix
 *       in later pass.
 *       E.g.: in 1st pass, collect all 'a's, 'b's, ... in 2nd pass, 
 *             for 'a's, sort them by 2nd char, then the same for 'b's...
 */
public class MSDRadixSort {
	
	int R = 256;
	
	public String[] sort(String[] input) {
		int len = input.length;
		String[] temp = new String[len];
		String[] output = Arrays.copyOf(input, len);
		sort(output, temp, 0, len-1, 0);
		return output;
	}
	
	// l: the lower bound of subarray
	// h: the higher bound of subarray
	// d: the dth char.
	// After 1st pass, l and h defines a subarray where all items inside it
	// have the same dth char.
	public void sort(String[] output, String[] temp, int l, int h, int d) {
		if(l >= h || d >= output[0].length()) return;
		
		int[] count = new int[R];
					
		for(int i = l; i <= h; i++) {
			count[output[i].charAt(d)]++;
		}
					
		for(int i = 1; i < R; i++) {
			count[i] += count[i-1];
		}
					
		for(int i = h; i >= l; i--) {
			int c = output[i].charAt(d);
			temp[count[c]-1] = output[i];
			count[c]--;
		}
		
		// output[i]: start from l to h of output
		// temp[i-l]: start from 0 to h-l of temp, where the items are sorted
		//            by dth char
		// output[l:h] = ["buy", "bay", "but"]
		// temp[0:h-l] = ["bay", "buy", "but"] by 2nd char
		for(int i = l; i <= h; i++) {
			output[i] = temp[i-l];
		}
		
//		System.arraycopy(temp, 0, output, l, h-l+1);
		
		for(int i = 0; i < R-1; i++) {
			sort(output, temp, l+count[i], l+count[i+1]-1, d+1);
		}
	}

	public static void main(String[] args) {
		MSDRadixSort msd = new MSDRadixSort();
		String[] arr = {"abs","cat", "abs", "eye", "cut", "arc", "car", "yes", "exe", "exc", "bye", "but", "cat"};
		for(String str:msd.sort(arr)) {
			System.out.println(str);
		}
	}

}
