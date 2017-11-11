package SurroundedRegions_130;

import java.util.ArrayDeque;
import java.util.LinkedList;
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

    // Second session
    // The idea is pretty simple: we can observe that
    // only those 'O' connected to the boundary can survive.
    // thus we scan the boundary 'O's and find the 'O's which are connected to
    // it. However we can't do it in DFS because there is a test case which
    // has a lot of 'O's on the bound, which will cost stack overflow if we use
    // DFS. So we use BFS..

    // note: BFS still cannot pass one of the test cases..
    // another good idea is to use a union-find.
    // connect all unsurrounded region to one dummy node.
    // and scan it another time.
    public static void solveII(char[][] board){
        if(board.length==0) return;
        int rows = board.length, cols = board[0].length;
        // we scan left and right bound first.
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<rows;i++){
            if(board[i][0]=='O') bfs(board,i,0,q);
            if(board[i][cols-1]=='O') bfs(board,i,cols-1,q);
        }

        for(int i=1;i<cols-1;i++){
            if(board[0][i]=='O') bfs(board,0,i,q);
            if(board[rows-1][i]=='O') bfs(board,rows-1,i,q);
        }

        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if (board[i][j] == '1') board[i][j] = 'O';
                else if (board[i][j] == 'O') board[i][j] = 'X';
            }
        }
    }

    private static int[][] delta = {{0,1},{0,-1},{1,0},{-1,0}};
    private static boolean valid(int i,int j,char[][] board){
        return i>=0&&j>=0&&i<board.length&&j<board[0].length&&board[i][j]=='O';
    }
    private static void bfs(char[][] board, int r, int c, Queue<Integer> q){
        q.add(r);
        q.add(c);
        while(q.size()!=0){
            int row = q.poll(), col = q.poll();
            board[row][col] = '1';
            for(int i=0;i<delta.length;i++){
                if(valid(row+delta[i][0],col+delta[i][1],board)){
                    q.add(row+delta[i][0]);
                    q.add(col+delta[i][1]);
                }
            }
        }
    }


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
