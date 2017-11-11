package BattleshipsInABoard_419;

/**
 * Given an 2D board, count how many battleships are in it.
 * The battleships are represented with 'X's, empty slots are represented with '.'s.
 * You may assume the following rules:
 *
 * You receive a valid board, made of only battleships or empty slots.
 * Battleships can only be placed horizontally or vertically.
 * In other words, they can only be made of the shape 1xN (1 row, N columns)
 * or Nx1 (N rows, 1 column), where N can be of any size.
 * At least one horizontal or vertical cell separates between
 * two battleships - there are no adjacent battleships.
 *
 * Example:
 * X..X
 * ...X
 * ...X
 * In the above board there are 2 battleships.
 *
 * Invalid Example:
 * ...X
 * XXXX
 * ...X
 * This is an invalid board that you will not receive -
 * as battleships will always have a cell separating between them.
 *
 * Follow up:
 * Could you do it in one-pass, using only O(1) extra memory
 * and without modifying the value of the board?
 */
public class Solution {

    // Second session
    public int countBattleshipsII(char[][] m){
        if(m.length==0) return 0;
        int count = 0;
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                if(m[i][j]=='X'){
                    if((i>0&&m[i-1][j]=='X')||(j>0&&m[i][j-1]=='X')) continue;
                    ++count;
                }
            }
        }
        return count;
    }

    // naive dfs solution
    public int countBattleships(char[][] board) {
        if(board.length==0) return 0;
        int count = 0;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]=='X'){
                    ++count;
                    dfs(board, i, j, 0);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] board, int r, int c, int flag){
        // notice we can have battleships in vertical or horizontal direction
        if(r>=board.length || c>=board[0].length || board[r][c] == '.') return;
        if(flag==0) {
            // start searching point of the battleship
            if(r+1 < board.length && board[r+1][c] == 'X') flag = 1;
            else if(c+1 < board[0].length && board[r][c+1] == 'X') flag = 2;
        }
        if(flag==1) dfs(board, r+1, c, flag);
        else if(flag==2) dfs(board, r, c+1, flag);
        board[r][c] = '.';
    }

    // one-pass no modifying O(1) space solution
    public int countBattleships2(char[][] board){
        if(board.length==0) return 0;
        int count = 0;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]=='X' && (i-1<0||board[i-1][j]=='.')
                        && (j-1<0||board[i][j-1]=='.'))
                    ++count;
            }
        }
        return count;
    }
}
