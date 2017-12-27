package string;


/*
 * Given a string and a pattern, find all start positions of the pattern
 * in the given string.
 * 
 * Complexity: 
 * 1.best: O(n), the first char in pattern doesn't appear
 * 2.worst: O(m*(n-m+1)), all chars are the same in str and pattern
 */
public class NaivePatternSearch {
	
	// Slide over chars one by one in str to check against pattern.
	public void PatternSearch(String txt, String pattern) {
		int n = txt.length();
		int m = pattern.length();
		for(int i = 0; i <= n - m; i++) {
			boolean match = true;
			for(int j = 0; j < m; j++) {
				if(txt.charAt(i + j) != pattern.charAt(j)) {
					match = false;
					break;
				}
			}
			if(match) {
				System.out.println("Pattern found at index " + i);
			}
		}
	}

	public static void main(String[] args) {
		String str = "AABAACAADAABAAABAA";
		String pattern = "AABA";
		String str1 = "AAAAAAAAAAAAA";
		String pattern1 = "AA";
		NaivePatternSearch nps = new NaivePatternSearch();
		nps.PatternSearch(str1, pattern1);
		nps.PatternSearch(str, pattern);
	}

}
