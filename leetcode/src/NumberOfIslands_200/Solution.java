package NumberOfIslands_200;

import java.util.Map;

/**
 * Given a 2d grid map of '1's (land) and '0's (water),
 * count the number of islands. An island is surrounded
 * by water and is formed by connecting adjacent lands horizontally or vertically.
 * You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 * 11110
 * 11010
 * 11000
 * 00000
 * Answer: 1
 *
 * Example 2:
 * 11000
 * 11000
 * 00100
 * 00011
 * Answer: 3
 */
public class Solution {
    // its not DP, its just drawing a map....
    // Notice! This is an unaccepted solution!!
    // most voted solution are using DFS
    public static int numIslands(char[][] g) {
        if(g.length==0) return 0;
        int rows = g.length,cols = g[0].length,islands = 0,islandsNo = 1;
        boolean[][] connected = new boolean[rows*cols/3][rows*cols/3];
        int[][] map = new int[rows+1][cols+1];
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if(g[i-1][j-1]=='1'){
                    if(map[i-1][j]==0&&map[i][j-1]==0){ // first met
                        islands++;
                        map[i][j] = islandsNo++; // mark it
                    }else if(map[i-1][j]==0&&map[i][j-1]!=0){ // connected
                        map[i][j] = map[i][j-1];
                    }else if(map[i-1][j]!=0&&map[i][j-1]==0){
                        map[i][j] = map[i-1][j];
                    }else if(map[i-1][j]!=map[i][j-1]){ // connected to different islands
                        if(!connected[map[i-1][j]][map[i][j-1]]){
                            islands--;
                            connected[map[i-1][j]][map[i][j-1]] = true;
                            connected[map[i][j-1]][map[i-1][j]] = true;
                        }
                        map[i][j] = Math.min(map[i-1][j],map[i][j-1]);
                    }else{ // equals but not zero
                        map[i][j] = map[i][j-1];
                    }
                }
            }
        }
        return islands;
    }

    public static void main(String[] args) {
        char[][] m1 = new char[][]{
                "11110".toCharArray(),
                "11010".toCharArray(),
                "11000".toCharArray(),
                "00000".toCharArray()
        };
        char[][] m2 = new char[][]{
                "11000".toCharArray(),
        };
        char[][] m3 = new char[][]{
                {'1'},
                {'0'},
                {'0'},
                {'1'}
        };
        char[][] m4 = new char[][]{
                "11000".toCharArray(),
                "11000".toCharArray(),
                "00100".toCharArray(),
                "00011".toCharArray()
        };
        char[][] m5 = new char[][]{
                "111".toCharArray(),
                "010".toCharArray(),
                "111".toCharArray()
        };
        System.out.println(numIslandsDFS(m1));
        System.out.println(numIslandsDFS(m2));
        System.out.println(numIslandsDFS(m3));
        System.out.println(numIslandsDFS(m4));
        System.out.println(numIslandsDFS(m5));
        System.out.println();
        System.out.println(numIslands(m1));
        System.out.println(numIslands(m2));
        System.out.println(numIslands(m3));
        System.out.println(numIslands(m4));
        System.out.println(numIslands(m5));
    }

    // DFS with memo
    public static int numIslandsDFS(char[][] g){
        if(g.length==0) return 0;
        int islands = 0;
        boolean[][] memo = new boolean[g.length][g[0].length];
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++)
                if(dfs(g,i,j,memo))
                    islands++;
        return islands;
    }

    private static boolean dfs(char[][] g,int i,int j,boolean[][] memo){
        if(i<0||j<0||i==g.length||j==g[0].length||memo[i][j]) return false;
        memo[i][j] = true;
        if(g[i][j]=='0') return false;
        dfs(g,i,j-1,memo);
        dfs(g,i-1,j,memo);
        dfs(g,i,j+1,memo);
        dfs(g,i+1,j,memo);
        return true; // return true because we have found a new island!
    }
}
