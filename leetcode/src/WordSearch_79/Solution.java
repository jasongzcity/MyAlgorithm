package WordSearch_79;

/**
 * Given a 2D board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * For example,
 * Given board =
 * [
 *   ['A','B','C','E'],
 *   ['S','F','C','S'],
 *   ['A','D','E','E']
 * ]
 * word = "ABCCED", -> returns true,
 * word = "SEE", -> returns true,
 * word = "ABCB", -> returns false.
 */
public class Solution {
    // most voted solution on leetcode.
    // using DFS & backtrack
    public static boolean exist(char[][] board, String word) {
        // assure board not empty!
        char[] a = word.toCharArray();
        for(int row=0;row<board.length;row++)
            for(int col=0;col<board[0].length;col++)
                if(correct(board,row,col,a,0))
                    return true;
        return false;
    }

    private static boolean correct(char[][] board,int row,int col,char[] a,int p){
        if(p==a.length) return true;
        if(row<0||row>=board.length||col<0||col>=board[0].length||
                a[p]!=board[row][col]) return false;
        board[row][col] = (char)~board[row][col];
        boolean rs = correct(board,row-1,col,a,p+1)||
                        correct(board,row+1,col,a,p+1)||
                        correct(board,row,col+1,a,p+1)||
                        correct(board,row,col-1,a,p+1);
        board[row][col] = (char)~board[row][col]; // backtrack
        return rs;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
        System.out.println(exist(board,"ABFDE"));
        System.out.println(exist(board,"DEDF"));
        System.out.println(exist(board,"CCEE"));
    }
}