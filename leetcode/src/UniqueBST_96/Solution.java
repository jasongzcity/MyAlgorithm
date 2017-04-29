package UniqueBST_96;

/**
 * Given n, how many structurally unique BST's (binary search trees)
 * that store values 1...n?
 *
 * For example,
 * Given n = 3, there are a total of 5 unique BST's.
 */
public class Solution {
    public static int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i=2;i<=n;i++){
            for(int j=0;j<i;j++){
                dp[i] += dp[j]*dp[i-1-j];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(numTrees(1));
    }
}
