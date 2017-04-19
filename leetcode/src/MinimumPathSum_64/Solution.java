package MinimumPathSum_64;

/**
 * Given a m x n grid filled with non-negative numbers,
 * find a path from top left to bottom right
 * which minimizes the sum of all numbers along its path.
 *
 * Note: You can only move either down or right at any point in time.
 */
public class Solution {
    // According to #63, we'd better not use DFS because it
    // would be very slow while the input is large.
    public static int minPathSum(int[][] grid) {
        // dp on the origin grid.
        int rowLen = grid[0].length,columnLen = grid.length;
        for (int i = 1; i < rowLen; i++) grid[0][i] = grid[0][i-1]+grid[0][i];
        for (int i = 1; i < columnLen; i++) grid[i][0] = grid[i-1][0]+grid[i][0];

        if(rowLen<2||columnLen<2) return grid[columnLen-1][rowLen-1];
        for(int i=1;i<columnLen;i++)
            for(int j=1;j<rowLen;j++)
                grid[i][j] = Math.min(grid[i-1][j],grid[i][j-1])+grid[i][j];
        return grid[columnLen-1][rowLen-1];
    }
}
