package UniquePathsII_63;

/**
 * Follow up for "Unique Paths":
 *
 * Now consider if some obstacles are added to the grids.
 * How many unique paths would there be?
 *
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 *
 * For example,
 *
 * There is one obstacle in the middle of a 3x3 grid as illustrated below.
 *
 * [
 *   [0,0,0],
 *   [0,1,0],
 *   [0,0,0]
 * ]
 * The total number of unique paths is 2.
 *
 * Note: m and n will be at most 100.
 */
public class Solution {
    public static int uniquePathsWithObstacles(int[][] o) {
        int rowLen = o[0].length,columnLen = o.length;
        int[][] dp = new int[columnLen][rowLen];
        for(int i=0;i<rowLen;i++) {
            if(o[0][i]==0) dp[0][i] = 1;
            else break;
        }

        for(int i=0;i<columnLen;i++) {
            if(o[i][0]==0) dp[i][0] = 1;
            else break;
        }

        if(rowLen<2||columnLen<2) return dp[columnLen-1][rowLen-1];
        for (int i = 1; i < columnLen; i++) {
            for (int j = 1; j < rowLen; j++) {
                if(o[i][j]==1) continue;
                dp[i][j] = dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[columnLen-1][rowLen-1];
    }

    public static void main(String[] args) {
        int[][] o = new int[][]{
                {0,0,0},
                {0,1,0},
                {0,0,0},
        };
        System.out.println(uniquePathsWithObstacles(o));
    }
}