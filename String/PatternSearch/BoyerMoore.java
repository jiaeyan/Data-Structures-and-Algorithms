package string;

import java.util.HashMap;
import java.util.Map;

// To get more intuitive understanding by graphs, see http://blog.jobbole.com/52830/

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
	
	/*
	 * Main idea: if there are some matched elements at the end of pat,
	 *            they are good suffix. We find the same good suffix at
	 *            other places in the pat, or find a prefix of the pat
	 *            that is also the suffix of the good suffix, and align them.
	 *            Otherwise, we skip the length of the whole pat.
	 */
	public void GoodSuffixSearch(String pat, String txt) {
		int m = pat.length();
		int n = txt.length();
		
		// A helper array to help compute gs
		// Each entry suffix[i] indicates a value k, where the substring
		// of length k in pat that ends at suffix[i], is the same as the
		// substring of length k in pat that ends at suffix[m-1].
		// m-1-i:   the fixed length of latter part of pat after i, doesn't change in each loop
		// m-1-i+j: the pointer of latter part of pat after i, start from end of pat
		// j:       the pointer of former part of pat before i, start from i of pat
		int[] suffix = new int[m];
		for(int i = m - 1; i >= 0; i--) {
			int j = i;
			while(j >= 0 && pat.charAt(j) == pat.charAt(m-1-i+j)) {
				j--;
			}
			// i is the end of former part, j = i at first, but moves backward later
			// on, so by i-j we get the steps it moves, a.k.a. the length of common suffix
			// between the former part and the whole pat.
			suffix[i] = i - j;
		}
		
		// Each entry indicates the skip step at current unmatched position
		// Three cases to process good suffix array.
		// Case 2 skips fewer than case 3, so do it first, update relative 
		// position's value in case 3 if possible.
		int[] goodsfx = new int[m];
		
		// Case 1: matched good suffix appears only once in pat, and 
		// no suffix of good suffix matched a prefix of pat.
		for(int i = 0; i < m; i++) {
			goodsfx[i] = m;
		}
		
		// Case 2: matched good suffix appears only once in pat, but
		// a suffix of good suffix matched a prefix of pat.
		// For each position i, the prefix length of pat till i is (i+1),
		// and since suffix[i] indicates the longest length of common suffix
		// between the substring before i and the whole pat, if suffix[i] = i+1,
		// this prefix [0-i] is also the suffix of the whole pat, thus is qualified
		// to fit case 2. 
		// Since this prefix is also suffix, its start position at the end of pat
		// is (m-1-suffix[i] = m-1-i-1), since it's only a part of the good suffix,
		// the unmatched position of the good suffix can be in range [0, m-1-i-1].
		// So if at any position in this range, the skip value is untouched as m
		// which we initialized in case 1, we set it to m-1-i, which is the skip length
		// from i to m-1.
		
		//TBD: why start from m-1? why j should be out of loop?
		int j = 0;
		for(int i = m - 1; i >= 0; i--) {
			if (suffix[i] == i + 1) {
				for(; j < m - 1 - i; j++) {
					if(goodsfx[j] == m) {
						goodsfx[j] = m - 1 - i;
					}
				}
			}
		}
		
		// Case 3: matched good suffix exists at other places in pat
		// For each position i, we know the longest common suffix length by suffix[i]
		// between substring before i (inclusive) and the whole pat. This means if we
		// match a suffix of length s of the pat, there must be the same suffix before,
		// and before this suffix must be the unmatched position. 
		// Since m-1 is the end position of pat, at (m-1-suffix[i]), we get the unmatched position,
		// and by m-1-i we get the length from i to m-1 that can be skipped, so
		// we can set the goodsfx as below:
		
		// m-1-suffix[i]: unmatched position
		// m-1-i: the step that can be skipped at the unmatched position above
		
		// Not able to reach m-1 for i, because suffix[m-1] must be m, which means 
		// skip the whole pat, by this goodsfx[m-1-m] = goodsfx[-1], not illegal.
		
		// Note since suffix[i] can be a same value for different i, goodsfx[m-1-suffix[i]]
		// can also lead the same position, and update its most recent (rightmost) postition's value.
		
		// For strong good suffix principle, we still have to check the preceding
		// element of this suffix[i] to be different from the one before the matched part, 
		// because that one must be unmatched.
		for(int i = 0; i < m - 1; i++) {
			goodsfx[m - 1 - suffix[i]] = m - 1 - i;
		}
		
		// Perform searching.
		int s = 0, t;
		while(s <= n - m) {
			t = m - 1;
			while(t >= 0 && pat.charAt(t) == txt.charAt(s+t)) {
				t--;
			}
			if(t < 0) {
				System.out.println("Pattern found at index: " + s);
				s += goodsfx[0];
			}
			else {
				s += goodsfx[t];
			}
		}
	}
	
	// Combine both badchar and good suffix solutions to get the bigger
	// one of them.
	// Here we use asicii values instead of hashmap to store bad char value;
	// and the value here indicates the skip distance rather than position
	// of last occurrence.
	public void ParallelSearch(String pat, String txt) {
		int m = pat.length();
		int n = txt.length();

		int[] badchar = preBC(pat, new int[256], m);
		int[] goodsfx = preGS(pat, new int[m], m);
		
		// i: check pointer in txt, keep moving forward
		// j: check pointer in pat, always start from the end of pat
		int i = 0;
		while (i <= n - m) {
			int j = m - 1;
			while(j >= 0 && pat.charAt(j) == txt.charAt(i+j)) {
				j--;
			}
			// If whole pat matched, j = -1
			if (j < 0) {
				System.out.println("Pattern found at index: " + i);
	            i += goodsfx[0];
	        }
			// Otherwise choose from goodsfx and badchar solution to find the
			// bigger skip step. For badchar solution
	        else {
	            i += Math.max(goodsfx[j], badchar[txt.charAt(i+j) - m + 1 + j]);
	        }
		}
	}
	
	public int[] preBC(String pat, int[] badchar, int m) {
		for (int i = 0; i < 256; ++i)
			badchar[i] = m;
		for (int i = 0; i < m - 1; ++i)
			badchar[pat.charAt(i)] = m - i - 1;
		return badchar;
	}
	
	public int[] preSFX(String pat, int[] suffix, int m) {
		for(int i = m - 1; i >= 0; i--) {
			int j = i;
			while(j >= 0 && pat.charAt(j) == pat.charAt(m-1-i+j)) {
				j--;
			}
			suffix[i] = i - j;
		}
		return suffix;
	}
	
	public int[] preGS(String pat, int[] goodsfx, int m) {
		int[] suffix = preSFX(pat, new int[m], m);
		
		// Case 1: no other good suffix in pat, nor common suffix/prefix
		for(int i = 0; i < m; i++) {
			goodsfx[i] = m;
		}
		
		// Case 2: no other good suffix in pat, but has common suffix/prefix
		int j = 0;
		for(int i = m - 1; i >= 0; i--) {
			if (suffix[i] == i + 1) {
				for(; j < m - 1 - i; j++) {
					if(goodsfx[j] == m) {
						goodsfx[j] = m - 1 - i;
					}
				}
			}
		}
		
		// Case 3: has other good suffix in pat
		for(int i = 0; i < m - 1; i++) {
			goodsfx[m - 1 - suffix[i]] = m - 1 - i;
		}
		
		return goodsfx;
	}

	public static void main(String[] args) {
		BoyerMoore bm = new BoyerMoore();
		
//		bm.BadCharSearch("TTCA", "AACACATCTTCA");
//		bm.BadCharSearch("EXAMPLE", "HERE IS A SIMPLE EXAMPLE");
//		bm.BadCharSearch("GEEK", "GEEKS FOR GEEKS");
//		bm.BadCharSearch("aabaaa", "aabaabaabaaaaabaabaaaaab");
		
//		bm.GoodSuffixSearch("EXAMPLE", "HERE IS A SIMPLE EXAMPLE");
//		bm.GoodSuffixSearch("aabaaa", "aabaabaabaaaaabaabaaaaab");
//		bm.GoodSuffixSearch("GEEK", "GEEKS FOR GEEKS");
//		bm.GoodSuffixSearch("TTCA", "AACACATCTTCA");
		
		bm.ParallelSearch("TTCA", "AACACATCTTCA");
		bm.ParallelSearch("GEEK", "GEEKS FOR GEEKS");
		bm.ParallelSearch("aabaaa", "aabaabaabaaaaabaabaaaaab");
		bm.ParallelSearch("EXAMPLE", "HERE IS A SIMPLE EXAMPLE");
	}

}
