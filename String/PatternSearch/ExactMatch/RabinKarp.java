package string;


/*
 * Main idea: Compute a hash value for pattern, take the same length substring
 *            from text and compute its hash value, if they are the same, compare
 *            elements one by one in both (in case of collision). 
 *            Slide one by one from text, but use former substring's hash to generate 
 *            next substring's hash, like from 'abcd' to compute 'bcde', only need to
 *            subtract 'a' and add 'e' to 'bcd', thus don't need to go over the whole substring
 *            again, otherwise the same as naive search.
 *            
 * Core: Need a powerful hash function to compute to get rid of collision. Generally
 *       use asicII values, and also adopt a prime number, and use modular algorithm.
 *       Also the hash function should be able to compute next shift value in O(1).
 * 
 * Usage: Multiple pattern match, given multiple same length patterns, check if they appear in
 *        a given document. Also able to check plagiarism, check if some pattern appear in
 *        multiple documents even in different orders.
 * 
 * Complexity: best - O(m + n), worst - O(m * n), where the weak hash function gives
 *             all substrings the same hash value, so the comparison will be the same
 *             as naive search.
 *       
 */
public class RabinKarp {
	
	private int prime = 101;
	
	public void search(String pat, String txt) {
		int m = pat.length();
		int n = txt.length();
		long p = 0;  //hash value of pattern
		long t = 0;  //hash value of first m chars in txt
		
		// Initialize values of p and t. The hash function we use is as below.
		// [Example] pattern: 'abc', the hash value will be: 
		// p = val(a)*prime^0 + val(b)*prime^1 + val(c)*prime^2;
		// For text 'abcd', we know the value of 'abc', to compute 'bcd',
		// we follow a 3-step procedure: 
		// 1. p = p - val(a)           -- drop 'a'
		// 2. p = p/prime              -- get 'val(b)*prime^0 + val(c)*prime^1'
		// 3. p = p + val(d)*prime^2   -- add 'd'
		for(int i = 0; i < m; i++) {
			p += pat.charAt(i) * Math.pow(prime, i);
			t += txt.charAt(i) * Math.pow(prime, i);
		}
		
		for(int i = 0; i <= n - m; i++) {
			// If hash values the same, check one by one
			if(p == t) {
				boolean flag = true;
				for(int j = 0; j < m; j++) {
					if(txt.charAt(i+j) != pat.charAt(j)) {
						flag = false;
						break;
					}
				}
				if(flag) {
					System.out.println("Pattern found at index: " + i);
				}
			}
			// If hash values differ, calculate the hash value of next shift.
			// Make sure i + m < n to avoid array index out of boundary.
			if(i < n - m) {
				t -= txt.charAt(i);
				t /= prime;
				t += txt.charAt(i + m) * Math.pow(prime, m - 1);;
			}
		}
	}

	public static void main(String[] args) {
		RabinKarp rk = new RabinKarp();
		rk.search("aabaaa", "aabaabaabaaaaabaabaaaaab");
	}

}
