package MaximumAreaOfIsland_695;

/**
 * Given a non-empty 2D array grid of 0's and 1's,
 * an island is a group of 1's (representing land)
 * connected 4-directionally (horizontal or vertical.)
 * You may assume all four edges of the grid are surrounded by water.
 *
 * Find the maximum area of an island in the given 2D array.
 * (If there is no island, the maximum area is 0.)
 *
 * Example 1:
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,1,1,0,1,0,0,0,0,0,0,0,0],
 * [0,1,0,0,1,1,0,0,1,0,1,0,0],
 * [0,1,0,0,1,1,0,0,1,1,1,0,0],
 * [0,0,0,0,0,0,0,0,0,0,1,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * Given the above grid, return 6.
 * Note the answer is not 11, because the island must be connected 4-directionally.
 *
 * Example 2:
 * [[0,0,0,0,0,0,0,0]]
 * Given the above grid, return 0.
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
public class Solution {
    // first thought:
    // it's a typical DFS problem.
    // the only thing we need to worry about is how to mark
    // the visited nodes?? Note that this is important in DFS
    // algorithm. If you don't do that you will get stack overflow.
    // this is so-called low-level mistakes..
    // In this problem, we simply mark them by changing them to 0s
    public int maxAreaOfIsland(int[][] g) {
        if(g.length==0) return 0;
        int max = 0;
        for(int i=0;i<g.length;i++){
            for(int j=0;j<g[0].length;j++){
                if(g[i][j]==1)
                    max = Math.max(dfs(g,i,j),max);
            }
        }
        return max;
    }

    private int dfs(int[][] g,int i,int j){
        if(i<0||j<0||i>=g.length||j>=g[0].length||g[i][j]==0) return 0;
        g[i][j] = 0;
        return 1+dfs(g,i+1,j)+dfs(g,i-1,j)+dfs(g,i,j-1)+dfs(g,i,j+1);
    }
}
