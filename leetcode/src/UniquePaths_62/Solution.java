package UniquePaths_62;

/**
 * A robot is located at the top-left corner of a m x n grid
 *
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid.
 * How many possible unique paths are there?
 *
 * Note: m and n will be at most 100.
 */
public class Solution {
    // I can figure out a DFS method but it may cause stack overflow
    // It becomes very slow when the input becomes larger.
    // For example (23,12)
    public static int uniquePaths(int m, int n) {
        return getPaths(1,1,m,n,0);
    }

    private static int getPaths(int cm,int cn,int m,int n,int rs){
        if(cm==m&&cn==n) return ++rs;

        if(cm<m) rs = getPaths(cm+1,cn,m,n,rs);
        if(cn<n) rs = getPaths(cm,cn+1,m,n,rs);
        return rs;
    }

    public static void main(String[] args) {
        System.out.println(uniquePaths(23,12));
        System.out.println(uniquePaths2(23,12));
    }

    // Dynamic programming solution
    public static int uniquePaths2(int m, int n) {
        int[][] dp = new int[m][n];

        // fill first column and first row
        for (int i = 0; i < n; i++) dp[0][i] = 1;
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        if(m<2||n<2) return dp[m-1][n-1];

        for(int i=1;i<m;i++)
            for(int j=1;j<n;j++)
                dp[i][j] = dp[i-1][j]+dp[i][j-1];
        return dp[m-1][n-1];
    }
}