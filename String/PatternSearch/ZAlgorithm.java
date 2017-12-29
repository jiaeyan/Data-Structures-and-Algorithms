package string;


/*
 * Main idea: concatenate the pat and txt with a separate symbol into a new string,
 *            move the pointer in the string, each position corresponds to a Z value,
 *            which indicates the longest substring length starting from that position 
 *            that matches a prefix of the whole string. Make this into a Z array, then
 *            check the Z array, the entry that has the same value of pat is the position
 *            that finds the pattern.
 *            
 *            The idea behind this design is to put the pat ahead of txt, make it a prefix
 *            of the concatenated string, and at each position of txt count the longest repeated
 *            elements that match the prefix, a.k.a the pat.
 *            
 *            Because the separate symbol doesn't appear in either part, every comparison must stop
 *            at the symbol, thus each Z[i] in the string corresponds to a value <= pat len. We detect
 *            the pattern by picking up the positions that have a value of pat len.
 * 
 * Usage: check the redundancy of the internal structure of given string.
 * 
 * Complexity: O(m+n), the Z array is concatenated by pat + txt, so we go through the array by (m+n)
 *             comparisons. Because we can reuse the z-box information, thus skip repeated pattern, thus
 *             save the complexity.
 * 
 * For more intuitive understanding by graphs, see: 
 * http://courses.csail.mit.edu/6.006/spring11/lectures/lec18-extra.pdf
 */
public class ZAlgorithm {

	public void search(String pat, String txt) {
		// We assume "$" doesn't appear in either pat or txt.
		// A separate symbol is needed because we want every comparison
		// to stop at the end of the pat, a.k.a. a certain prefix. 
		String concat = pat + "$" + txt;
		
		int n = concat.length();
		int m = pat.length();
		
		int[] z = constructZ(concat, new int[n], n);
		
		// Simply pick up the entries that have value of pat len m.
		// Since pat len m and 1 separate symbol ahead of txt, the 
		// original position at txt can be computed by i-m-1
		for(int i = 1; i < n; i++) {
			if(z[i] == m) {
				System.out.println("Pattern found at index: " + (i-m-1));
			}
		}
		
		
	}
	
	public int[] constructZ(String concat, int[] z, int n) {
		// Each matched comparison defines a z-box, which starts at l
		// and ends at r, z-box of concat[l:r] is the same as some prefix of its own.
		// Some z-box can be so big that it covers some check pointers later on.
		// Since a z-box is also the prefix, if a pointer i lies in the z-box,
		// there is also a relative point k lies in the prefix, and we already computes
		// that k's Z value, no need to compute i again, just reuse it.
		// However, this reuse is based on some condition. See explanation below.
		
		// Note the matched prefix length must <= pat len m, so the length of 
		// z-box also lies in [1, m].
		// If r-l=0, means no match, no z-box.
		int l = 0, r = 0;
		
		// The goal of this loop is to compute z values of each position of z array.
		for(int i = 1; i < n; i++) {
			// If i is outside of last z-box, no reusable info from it, simply
			// restart everything.
			if(i > r) {
				l = r = i;
				
				// Fix l bound and increment r bound, so we get a z-box.
				while(r < n && concat.charAt(r) == concat.charAt(r - l)) {
					r++;
				}
				z[i] = r - l;
				
				// After a z-box defined, the r pointer is at the unmatched position,
				// since the r bound of z-box should be inclusive/matched, do r--.
				r--;
			}
			
			// If i is inside of last z-box, we can reuse the info from it.
			// k: the point that corresponds to i in the prefix in the z-box
			// z[k]: the prefix length matched from k
			// r-i+1: the remaining length from i to r in last z-box
			else {
				int k = i - l;
				
				// If prefix length matched from k is smaller than the remaining
				// of the z-box, it's safe to copy values from z-box.
				if(z[k] < r-i+1) {
					z[i] = z[k];
				}
				
				// If prefix length matched from k is beyond the remaining of
				// z-box, not safe to copy everything, because the substring start
				// from position i may match a longer prefix, so have to shrink
				// the z-box by making its l bound as i, and check one by one to
				// extend the r bound to form new z-box.
				else {
					l = i;
					while(r < n && concat.charAt(r) == concat.charAt(r - l)) {
						r++;
					}
					z[i] = r - l;
					r--;
				}
			}
		}
		
		return z;
	}

	public static void main(String[] args) {
		ZAlgorithm za = new ZAlgorithm();
		za.search("TTCA", "AACACATCTTCA");
		System.out.println();
		za.search("GEEK", "GEEKS FOR GEEKS");
		System.out.println();
		za.search("aabaaa", "aabaabaabaaaaabaabaaaaab");
		System.out.println();
		za.search("EXAMPLE", "HERE IS A SIMPLE EXAMPLE");
	}

}
