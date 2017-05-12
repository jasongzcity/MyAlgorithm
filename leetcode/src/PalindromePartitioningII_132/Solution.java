package PalindromePartitioningII_132;

/**
 * Given a string s, partition s such that every substring of the partition
 * is a palindrome.
 *
 * Return the minimum cuts needed for a palindrome partitioning of s.
 *
 * For example, given s = "aab",
 * Return 1 since the palindrome partitioning ["aa","b"]
 * could be produced using 1 cut.
 */
public class Solution {
    // inspired by the most voted solution on leetcode
    public static int minCut(String s) {
        int size = s.length();
        // dp[i] represents the minimum cuts in the previous
        // i characters
        int[] dp = new int[size+1];
        for(int i=0;i<=size;i++) dp[i] = i-1; // initialize

        // DP
        for(int i=0;i<size;i++){
            // check odd length palindrome
            for(int j=0;i-j>=0&&i+j<size&&s.charAt(i-j)==s.charAt(i+j);j++)
                if(1+dp[i-j]<dp[i+j+1])
                    dp[i+j+1] = 1+dp[i-j];
            // check even length palindrome
            for(int j=1;i-j>=-1&&i+j<size&&s.charAt(i-j+1)==s.charAt(i+j);j++)
                if(1+dp[i-j+1]<dp[i+j+1])
                    dp[i+j+1] = 1+dp[i-j+1];
        }
        return dp[size];
    }

    // an improvement of the solution above
    // it use a matrix as a memo
    public static int minCut2(String s) {
        char[] c = s.toCharArray();
        int n = c.length;
        int[] cut = new int[n];
        boolean[][] pal = new boolean[n][n];
        // pal[i,j] represents whether s.subString(i,j+1) is
        // palindrome

        for(int i = 0; i < n; i++) {
            int min = i;
            for(int j = 0; j <= i; j++) {
                if(c[j] == c[i] && (j + 1 > i - 1 || pal[j + 1][i - 1])) {
                    pal[j][i] = true;
                    min = j == 0 ? 0 : Math.min(min, cut[j - 1] + 1);
                }
            }
            cut[i] = min;
        }
        return cut[n - 1];
    }
}
