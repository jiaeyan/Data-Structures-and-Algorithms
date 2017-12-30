package string;

public class WildcardMatch {

	public boolean match(String pat, String txt) {
		int m = pat.length();
		int n = txt.length();
		
		boolean[][] dp = new boolean[n+1][m+1];
		dp[0][0] = true;
		
		for(int i = 1; i < n + 1; i++) {
			for(int j = 1; j < m + 1; j++) {
				if(pat.charAt(j-1) == '?' || pat.charAt(j-1) == txt.charAt(i-1)) {
					dp[i][j] = dp[i-1][j-1];
				}
				else if (pat.charAt(j-1) == '*') {
					dp[i][j] = dp[i-1][j] || dp[i][j-1];
				}
				else {
					dp[i][j] = false;
				}
			}
		}
		
		return dp[n][m];
	}

	public static void main(String[] args) {
		WildcardMatch wm = new WildcardMatch();
		if(wm.match("x?y*z", "xbylmz")) System.out.println("Yes");
		else System.out.println("No");
	}

}
