package IslandPerimeter_463;

/**
 * You are given a map in form of a two-dimensional
 * integer grid where 1 represents land and 0 represents water.
 * Grid cells are connected horizontally/vertically (not diagonally).
 * The grid is completely surrounded by water, and there is exactly one island
 * (i.e., one or more connected land cells).
 * The island doesn't have "lakes" (water inside that isn't
 * connected to the water around the island).
 * One cell is a square with side length 1.
 * The grid is rectangular, width and height don't exceed 100.
 * Determine the perimeter of the island.
 *
 * Example:
 * [[0,1,0,0],
 * [1,1,1,0],
 * [0,1,0,0],
 * [1,1,0,0]]
 * Answer: 16
 * Explanation: The perimeter is the 16 yellow stripes in the image below:
 */
public class Solution {

    private int count = 0;

    // optimal solution is not using dfs!
    public int islandPerimeter2(int[][] g) {
        // assume there must exists one island
        boolean[][] visited = new boolean[g.length][g[0].length];
        for(int i=0;i<g.length;i++){
            for(int j=0;j<g[0].length;j++){
                if(g[i][j]==1&&!visited[i][j])
                    dfs(g,i,j,visited);
            }
        }
        return count;
    }

    // return true if this side needed to add
    // return false if it's island
    private boolean dfs(int[][] g, int i, int j, boolean[][] visited){
        if(i<0||j<0||i>=g.length||j>=g[0].length||g[i][j]==0) return true;

        if(!visited[i][j]){
            visited[i][j] = true;
            if(dfs(g,i-1,j,visited)) ++count;
            if(dfs(g,i+1,j,visited)) ++count;
            if(dfs(g,i,j-1,visited)) ++count;
            if(dfs(g,i,j+1,visited)) ++count;
        }

        return false;
    }

    // optimal solution
    // "counting neighbors and islands"
    // I count the upper and left neighbor instead
    public int islandPerimeter(int[][] g){
        int island = 0, neighbor = 0;
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                if(g[i][j]==1){
                    island++;
                    if(i>0&&g[i-1][j]==1) ++neighbor; // upper neighbor
                    if(j>0&&g[i][j-1]==1) ++neighbor;
                }
            }
        }
        return island*4-neighbor*2;
    }


    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] g = new int[][]{
                {0,1,0,0},
                {1,1,1,0},
                {0,1,0,0},
                {1,1,0,0}
        };
        System.out.println(s.islandPerimeter(g));
    }
}
