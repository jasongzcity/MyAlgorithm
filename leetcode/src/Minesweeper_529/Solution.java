package Minesweeper_529;

import java.util.*;

/**
 * Let's play the minesweeper game (Wikipedia, online game)!
 *
 * You are given a 2D char matrix representing the game board.
 * 'M' represents an unrevealed mine, 'E' represents an unrevealed empty square,
 * 'B' represents a revealed blank square that has no adjacent
 * (above, below, left, right, and all 4 diagonals) mines,
 * digit ('1' to '8') represents how many mines are adjacent
 * to this revealed square, and finally 'X' represents a revealed mine.
 *
 * Now given the next click position (row and column indices)
 * among all the unrevealed squares ('M' or 'E'),
 * return the board after revealing this position according to the following rules:
 *
 * If a mine ('M') is revealed, then the game is over - change it to 'X'.
 * If an empty square ('E') with no adjacent mines is revealed,
 * then change it to revealed blank ('B') and all of its adjacent
 * unrevealed squares should be revealed recursively.
 * If an empty square ('E') with at least one adjacent mine is revealed,
 * then change it to a digit ('1' to '8') representing the number of adjacent mines.
 *
 * Return the board when no more squares will be revealed.
 * Example 1:
 * Input:
 * [['E', 'E', 'E', 'E', 'E'],
 * ['E', 'E', 'M', 'E', 'E'],
 * ['E', 'E', 'E', 'E', 'E'],
 * ['E', 'E', 'E', 'E', 'E']]
 *
 * Click : [3,0]
 *
 * Output:
 * [['B', '1', 'E', '1', 'B'],
 * ['B', '1', 'M', '1', 'B'],
 * ['B', '1', '1', '1', 'B'],
 * ['B', 'B', 'B', 'B', 'B']]
 *
 * Example 2:
 * Input:
 * [['B', '1', 'E', '1', 'B'],
 * ['B', '1', 'M', '1', 'B'],
 * ['B', '1', '1', '1', 'B'],
 * ['B', 'B', 'B', 'B', 'B']]
 *
 * Click : [1,2]
 *
 * Output:
 * [['B', '1', 'E', '1', 'B'],
 * ['B', '1', 'X', '1', 'B'],
 * ['B', '1', '1', '1', 'B'],
 * ['B', 'B', 'B', 'B', 'B']]
 *
 * Note:
 * The range of the input matrix's height and width is [1,50].
 * The click position will only be an unrevealed square ('M' or 'E'),
 * which also means the input board contains at least one clickable square.
 * The input board won't be a stage when game is over
 * (some mines have been revealed).
 * For simplicity, not mentioned rules should be ignored in this problem.
 * For example, you don't need to reveal all the unrevealed mines
 * when the game is over, consider any cases that
 * you will win the game or flag any squares.
 */
public class Solution {

    // my solution got screwed when I use BFS.....
    // here is the optimal solution on leetcode
    public char[][] updateBoard(char[][] b, int[] click){
        int cx = click[0],cy = click[1];
        if(b[cx][cy]=='M') b[cx][cy] = 'X';
        else if(b[cx][cy]=='E') dfs(b,cx,cy);
        return b;
    }

    private void dfs(char[][] b,int x,int y){
        if(x<0||y<0||x>=b.length||y>=b[0].length||b[x][y]!='E') return;
        int count = 0;
        if(x-1>-1){
            if(y-1>-1&&b[x-1][y-1]=='M') ++count;
            if(b[x-1][y]=='M') ++count;
            if(y+1<b[0].length&&b[x-1][y+1]=='M') ++count;
        }
        if(y+1<b[0].length){
            if(b[x][y+1]=='M') ++count;
            if(x+1<b.length&&b[x+1][y+1]=='M') ++count;
        }
        if(x+1<b.length){
            if(b[x+1][y]=='M') ++count;
            if(y-1>-1&&b[x+1][y-1]=='M') ++count;
        }
        if(y-1>-1&&b[x][y-1]=='M') ++count;
        if(count>0) b[x][y] = (char)(count+'0');
        else{
            b[x][y] = 'B';
            dfs(b,x-1,y-1);
            dfs(b,x-1,y);
            dfs(b,x-1,y+1);
            dfs(b,x,y+1);
            dfs(b,x+1,y+1);
            dfs(b,x+1,y);
            dfs(b,x+1,y-1);
            dfs(b,x,y-1);
        }
    }

    public static void main(String[] args) {
        char[][] b = new char[][]{
                "EEEEE".toCharArray(),
                "EEMEE".toCharArray(),
                "EEEEE".toCharArray(),
                "EEEEE".toCharArray()
        };
        Solution s = new Solution();
        s.updateBoard(b,new int[]{3,0});
    }
}
