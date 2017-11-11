package WallsAndGates_286;

/**
 * You are given a m x n 2D grid initialized with these three possible values.
 *
 * -1 - A wall or an obstacle.
 * 0 - A gate.
 * INF - Infinity means an empty room.
 * We use the value 2^31 - 1 = 2147483647 to represent INF
 * as you may assume that the distance to a gate is less than 2147483647.
 * Fill each empty room with the distance to its nearest gate.
 * If it is impossible to reach a gate, it should be filled with INF.
 *
 * For example, given the 2D grid:
 * INF  -1  0  INF
 * INF INF INF  -1
 * INF  -1 INF  -1
 * 0    -1 INF INF
 * After running your function, the 2D grid should be:
 * 3  -1   0   1
 * 2   2   1  -1
 * 1  -1   2  -1
 * 0  -1   3   4
 */
public class Solution {

    // second session.
    // first thought:
    // we do DFS begin from a gate, get around the walls and minimizing the distance
    // from the gate
    public void wallsAndGates2(int[][] rooms){
        if(rooms.length==0) return;
        for(int i=0;i<rooms.length;i++){
            for(int j=0;j<rooms[0].length;j++){
                if(rooms[i][j]==0)
                    dfs2(rooms,i,j,0);
            }
        }
    }

    private void dfs2(int[][] r, int i, int j, int dis){
        if(i>=0&&j>=0&&i<r.length&&j<r[0].length&&r[i][j]>=dis){
            r[i][j] = dis;
            dfs2(r,i-1,j,dis+1);
            dfs2(r,i+1,j,dis+1);
            dfs2(r,i,j-1,dis+1);
            dfs2(r,i,j+1,dis+1);
        }
    }

    public void wallsAndGates(int[][] rooms) {
        for(int i=0;i<rooms.length;i++)
            for(int j=0;j<rooms[0].length;j++)
                if(rooms[i][j]==0)
                    dfs(rooms,i,j,0);
    }

    private void dfs(int[][] rooms,int row,int col,int dis){
        if(row>-1&&row<rooms.length&&col>-1&&col<rooms[0].length&&
                rooms[row][col]>=dis){
            rooms[row][col] = dis;
            dfs(rooms,row-1,col,dis+1);
            dfs(rooms,row+1,col,dis+1);
            dfs(rooms,row,col+1,dis+1);
            dfs(rooms,row,col-1,dis+1);
        }
    }
}
