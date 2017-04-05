package SudokuSolver_37;

import java.util.Stack;

/**
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 */
public class Solution
{
    public static void solveSudoku(char[][] board)
    {
        boolean[][] row_map = new boolean[9][9];
        boolean[][] column_map = new boolean[9][9];
        boolean[][] court_map = new boolean[9][9];
        boolean[][] changable = new boolean[9][9];
        Stack<Integer> stack = new Stack<>();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int num = -1;
                if(changable[row][column]||board[row][column]=='.'){
                    if(board[row][column]!='.'){ // backtrack
                        num = board[row][column] - '0'+1; // che
                    }else{ // first met
                        num = 1;
                        changable[row][column] = true;
                    }
                    while()
                }
            }
        }
    }



    public static boolean isValid(int num,int row,int column,boolean[][] row_map,
                                  boolean[][] column_map,boolean[][] court_map)
    {
        if(row_map[row][num]||column_map[column][num]||
                court_map[row/3*3+column/3][num]) return false;
        else return true;
    }


//    static void fillBoard(char[][] board, int row, int column, Stack<Integer> stack,
//                          boolean[][] row_map, boolean[][] column_map,
//                          boolean[][] court_map)
//    {
//        if(board[row][column]=='.')
//        {
//
//        }
//
//    }
}
