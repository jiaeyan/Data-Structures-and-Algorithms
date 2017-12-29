package string;

import java.util.HashMap;
import java.util.Map;

public class BoyerMoore {
	
	/*
	 * Main idea: align pat and txt, compare the last element, if the same, go backward
	 *            keep checking; if not the same, find the last occurrence of unmatched
	 *            element from txt in pat: if exists, align them; if not, align with -1.
	 *            
	 * Complexity: worst: O(m*n), where all elements in txt are the same; 
	 *             best: O(n/m), where no pattern matched, skip the length
	 *                   of pattern all the time to the end of txt.
	 */
	public void BadCharSearch(String pat, String txt) {
		int m = pat.length();
		int n = txt.length();
		
		// A hashmap record the last occurrence of every element in pattern
		Map<Character, Integer> badchar = new HashMap<>();
		for(int i = 0; i < m; i++) {
			badchar.put(pat.charAt(i), i);
		}

		int i = 0;
		while(i <= n - m) {
			
			// Every comparison starts from the last element of pattern
			int j = m - 1;

			// Only allowed to push back the length of m, pattern length, no further
			while (j >= 0 && txt.charAt(i+j) == pat.charAt(j)) {
				j--;
			}
			
			// If the whole pattern matched, j becomes -1.
			// If i+m < n, find the next char txt[i+m], check if it is in pat, if yes,
			// find its last occurrence and move txt's pointer; if no, step is -1.
			// If i+m >= n, no next char, then move txt pointer by 1, breaks next loop.
			if (j < 0) {
				System.out.println("Pattern found at index: " + i);
				i += i + m < n ? m - badchar.getOrDefault(txt.charAt(i+m), -1) : 1;				
			}
			
			// Otherwise part of pattern matched, shift the pattern.
			// For pattern, shift means move forward, while for txt, shift pat
			// means to move the pointer to align the pat, where pointer i points 
			// to the 1st element of shifted pat. In other words, the pointer i moves
			// the same step as pattern.
			// Backward shift: 'ASIMPLI'
			//                 'EXAMPLI'
			//                 'I' and 'A' don't match, and now j = 2, badchar[I] = 6,
			//                  where 2 - 6 = -4, means pat has to shift back, no! Note 
			//                  now the last occurrence of bad char is at the right side of
			//                  current char. Solution is to just shift pat forward by 1.
			// Forward shift:  'ABCDABE'
			//                 'BCDA'
			//                 'D' and 'A' don't match, and now j = 3, badchar[D] = 2,
			//                  where 3 - 2 = 1, means pat has to shift forward by 1, yes!
			//                  Note the last occurrence of bad char is at the left side of current char.
			else {
				char mismatch = txt.charAt(i+j);
				int step = badchar.getOrDefault(mismatch, -1);
				i += Math.max(1, j - step); // now i points to 1st element of shifted pat, aligned
			}
		}
	}
	
	public void GoodSuffixSearch(String pat, String txt) {
		int m = pat.length();
		int n = txt.length();
		int[] suffix = new int[m];
		
	}

	public static void main(String[] args) {
		BoyerMoore bm = new BoyerMoore();
//		bm.BadCharSearch("TTCA", "AACACATCTTCA");
		bm.BadCharSearch("EXAMPLE", "HERE IS A SIMPLE EXAMPLE");
//		bm.BadCharSearch("GEEK", "GEEKS FOR GEEKS");
//		bm.BadCharSearch("aabaaa", "aabaabaabaaaaabaabaaaaab");
	}

}
