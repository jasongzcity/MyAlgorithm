package SurroundedRegions_130;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Given a 2D board containing 'X' and 'O' (the letter O),
 * capture all regions surrounded by 'X'.
 *
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 *
 * For example,
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 *
 * After running your function, the board should be:
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 */
public class Solution {
    // DFS with memo to memorize if a position has been visited.
    // Its just like classic DFS
    // Notice: this solution would cause Stackoverflow........
    // imagine the input is a large matrix with all 'O'
    public static void solve(char[][] board) {
        int rows = board.length;
        if(rows==0) return;
        int cols = board[0].length;
        for(int i=0;i<rows;i++) {
            if (board[i][cols - 1] == 'O') walk(board, i, cols - 1);
            if (board[i][0] == 'O') walk(board, i, 0);
        }
        for(int i=1;i<cols-1;i++) {
            if (board[rows - 1][i] == 'O') walk(board, rows - 1, i);
            if (board[0][i] == 'O') walk(board, 0, i);
        }

        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++){
                if (board[i][j] == '1') board[i][j] = 'O';
                else if (board[i][j] == 'O') board[i][j] = 'X';
            }
    }

    // DFS walk
    // fuck the test case!!!
    // which has many 'O's in the first row and will cause overflow....
    // fuck
    private static void walk(char[][] board,int row,int col){
        board[row][col] = '1';
        if(row>1&&board[row-1][col]=='O') walk(board,row-1,col);
        if(row<board.length-1&&board[row+1][col]=='O') walk(board,row+1,col);
        if(col>1&&board[row][col-1]=='O') walk(board,row,col-1);
        if(col<board[0].length-1&&board[row][col+1]=='O') walk(board,row,col+1);
    }

    private static void walk2(char[][] board,int row,int col){
        // memo[i][j] represents if we have visited this position before
        Queue<Integer> q = new ArrayDeque<>();
        q.add(row);
        q.add(col);
        while(!q.isEmpty()){
            int r = q.poll(),c = q.poll();
            board[r][c] = '1';
            if(r>0&&board[r-1][c]=='O') { q.add(r-1); q.add(c); }
            if(r<board.length-1&&board[r+1][c]=='O') { q.add(r+1); q.add(c); }
            if(c>0&&board[r][c-1]=='O') { q.add(r); q.add(c-1); }
            if(c<board[0].length-1&&board[r][c+1]=='O') { q.add(r); q.add(c+1); }
        }
    }
}
