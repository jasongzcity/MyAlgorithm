package NQueenII_52;

/**
 * Follow up for N-Queens problem.
 *
 * Now,instead outputting board configurations,
 * return the total number of distinct solutions.
 */
public class Solution {
    public static int totalNQueens(int n) {
        char[][] board = new char[n][n];
        return putQueenInLine(board,0,0);
    }

    private static int putQueenInLine(char[][] board,int line,int rs){
        if(line==board.length){ // base, get solution
            return ++rs;
        }

        for (int i = 0; i < board.length; i++) {
            if(isAvailable(board,line,i)){
                board[line][i] = 'Q';
                rs = putQueenInLine(board,line+1,rs);
                board[line][i] = 0; // prepare for backtrack
            }
        }
        return rs;
    }

    private static boolean isAvailable(char[][] board,int x,int y){
        int left = y-1,right = y+1;
        for(int i=x-1;i>-1;i--){
            if(board[i][y]=='Q'||(left>-1&&board[i][left]=='Q')||
                    (right<board.length&&board[i][right]=='Q')) return false;
            left--;
            right++;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(totalNQueens(8));
    }
}
