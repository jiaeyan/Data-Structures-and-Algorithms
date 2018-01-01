package string;


/*
 * Main idea: skip some matched substring, rather than sliding 1 by 1 when match misses.
 * 
 * Text:       a b c a b c a b e
 * Pattern:    a b c a b e
 * Match:      a b c a b
 * Pre/suffix: a b
 * Next:       a b c a b c a b e
 *             ->    a b c a b e
 *             
 * Assumption: a substring/prefix of pattern matches the text, and
 * this substring shares the same proper prefix and suffix.
 * 
 * For text and pattern, everything before the unmatched letters is
 * the same. If the suffix of the text's matched part is the same as the 
 * pattern's prefix, a.k.a., several letters before the unmatched letter 
 * in the text are the same as several letters at the beginning of the pattern, 
 * we can directly align them, sine every comparison before that must fail,
 * and by this we save many unnecessary comparisons (the prefix and suffix may overlap).
 * Since each position of the pattern can be an unmatched letter, we want to 
 * know in the matched part till this letter, the longest length of common
 * proper prefix and suffix, and each position will record that length. This value
 * indicates how many comparisons we can save/move forwards (matched number - length)
 * when a mismatch happens.
 * 
 * Complexity: O(m + n), while m = len(pat) and n = len(txt)
 */
public class KnuthMorrisPratt {
	
	private String pat;
	private int LPS[];
	
	// The constructor preprocesses the pattern into LPS
	// (longest common proper prefix and suffix: the whole string not allowed,
	// because if allowed, the LPS will always be the whole string, meaningless).
	// Pattern: a  b  c  a  b  d  e  f  a  b  c   a   b   c
	// Index:   0  1  2  3  4  5  6  7  8  9  10  11  12  13
	// LPS:     0  0  0  1  2  0  0  0  1  2  3   4   5   ?
	// Note that LPS not only means lps length till current position, it also indicates
	// the next check point to grow the lps, the pointer of lps.
	// We have pat[0-4] = pat[8-12] = abcab, but pat[5] = d != pat[13] = c,
	// and now lps or len = 5, so we know impossible to reach 6 (because d != c), we
	// go back to check what's the lps in len = 4, a.k.a. in pat[0-4] to get the as longest
	// as possible lps before pat[5], so we go to the prefix of length (len-1), to see
	// what's the longest lps there, thus len = lps[len-1]. Since len also indicates the 
	// next check point, we directly check pat[len] with pat[i], which means everything before
	// the check point pat[len] is already the same as things before pat[i], because pat[0-4] = pat[8-12],
	// and suffix pat[8-9] in pat[8-12] = prefix pat[0-1] in pat[0-4], which is ab.
	// If this comparison succeeds, len++, the prefix of pattern grows, otherwise find a shorter lps.
	// Keep this procedure until pat[len] = pat[i] or len = 0, a.k.a. back to the start point.
	public KnuthMorrisPratt(String pattern) {
		pat = pattern;
		int m = pat.length();
		
		// Each index represents the letter in the pattern, and its value represents
		// the longest proper prefix/suffix of the substring till current index.
		LPS = new int[m];
		
		// Set 2 values, 'len' indicates the lps length of matched part till 
		// current index, while 'i' indicates the index of current position.
		int len = 0, i = 1;
		
		// The 1st letter in pattern has no previous substring, thus lps is 0.
		LPS[0] = len;
		
		while(i < m) {
			if(pat.charAt(i) == pat.charAt(len)) {
				LPS[i] = len + 1; //LPS[i++] = len++ + 1;
				i++;
				len++;
			}
			else if(len != 0) {
				len = LPS[len - 1];
			}
			else {
				LPS[i] = 0; //LPS[i++] = 0;
				i++;
				}
		}
		
		// A for-loop approach: for every iteration, calculate LPS[i]
//		for(int i = 1; i < m; i++) {
//			while (pat[len] != pat[i]) {
//				if(len != 0) {
//					len = LPS[len - 1];
//				} 
//				else {
//					LPS[i] = 0;
//					break;
//				} 
//			}
//			if(pat[len] == pat[i]) {
//				len++;
//				LPS[i] = len;
//			}
//		}
	}
	
	// We assume len(pat) <= len(txt).
	public void search(String txt) {
		int n = txt.length();
		int m = pat.length();
		int i = 0, j = 0; //index of txt and pat
		while(i < n) {
			// If txt[i] and pat[j] matched, move forward both pointers and go ahead
			if (txt.charAt(i) == pat.charAt(j)) {
				i++;
				j++;
				// Once a pattern finished, report, and find the next check point to 
				// save unnecessary comparisons by LPS (see explanation below).
				if(j == m) {
					System.out.println("Pattern found at index: " + (i - j));
					j = LPS[j - 1];
				}
			}
			// If not matched, and if a substring of pat[0 to j-1] already matched,
			// which means, before pat[j], everything is the same between pat and txt,
			// so we find the lps length of the common part before pat[j], this lps is 
			// the longest suffix of the txt before txt[i], also is the longest prefix 
			// of pat before pat[j], we directly align them by moving pointer j to the 
			// next check point, to skip over unnecessary comparisons (this is the goal and
			// mechanism of LPS)
			else if (j != 0) {
				j = LPS[j - 1];
			}
			// If not matched at the 1st letter in the pattern, simply move one letter forward
			// of the text, try to match next letter of text with the 1st letter of the pattern.
			else i++;
		}
	}

	public static void main(String[] args) {
		KnuthMorrisPratt kmp = new KnuthMorrisPratt("aabaaa");
		kmp.search("aabaabaabaaa aabaabaaaaab");
		for(int i:kmp.LPS) {System.out.println(i);}
	}

}
