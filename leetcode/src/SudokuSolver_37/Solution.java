package SudokuSolver_37;

import java.util.Stack;

/**
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 */
public class Solution {
    public static void solveSudoku(char[][] board) {
        boolean[][] row_map = new boolean[9][9];
        boolean[][] column_map = new boolean[9][9];
        boolean[][] court_map = new boolean[9][9];
        boolean backtrackFlag = false;
        Stack<Integer> stack = new Stack<>();
        for (int row = 0; row < 9; row++)
            for (int column = 0; column < 9; column++)
                if(board[row][column]!='.')
                    setMap(board[row][column]-'0'-1,
                            row,column,row_map,column_map,court_map);

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; ) {
                int num = -1;
                if (backtrackFlag || board[row][column] == '.') {
                    if (board[row][column] == '.') {
                        num = 1;
                    } else { // backtrack
                        num = board[row][column] - '0' + 1; // check next num
                        resetMap(num - 2, row, column, row_map, column_map, court_map);
                    }
                    while (num < 10 && !isValid(num - 1, row, column, row_map, column_map, court_map)) num++;
                    if (num == 10) { // all num invalid, backtrack
                        board[row][column] = '.';
                        column = stack.pop();
                        row = stack.pop();
                        backtrackFlag = true;
                        continue;
                    } else {  // got a valid number
                        board[row][column] = (char) (num + '0');
                        stack.push(row);
                        stack.push(column);
                        backtrackFlag = false; // search next column
                    }
                } else {
                    num = board[row][column] - '0';
                }
                setMap(num - 1, row, column, row_map, column_map, court_map);
                column++;
            }
        }
    }

    // shortcut for better readability
    public static void resetMap(int num, int row, int column, boolean[][] row_map,
                                boolean[][] column_map, boolean[][] court_map) {
        row_map[row][num] = false;
        column_map[column][num] = false;
        court_map[row / 3 * 3 + column / 3][num] = false;
    }

    // shortcut for better readability
    public static void setMap(int num, int row, int column, boolean[][] row_map,
                              boolean[][] column_map, boolean[][] court_map) {
        row_map[row][num] = true;
        column_map[column][num] = true;
        court_map[row / 3 * 3 + column / 3][num] = true;
    }


    public static boolean isValid(int num, int row, int column, boolean[][] row_map,
                                  boolean[][] column_map, boolean[][] court_map) {
        if (row_map[row][num] || column_map[column][num] ||
                court_map[row / 3 * 3 + column / 3][num]) return false;
        else return true;
    }

    public static void main(String[] args) {
        String[] strs = new String[]{"..9748...", "7........",
                ".2.1.9...", "..7...24.", ".64.1.59.", ".98...3..",
                "...8.3.2.", "........6", "...2759.."};
        char[][] board = new char[9][9];
        for (int i = 0; i < 9; i++) {
            board[i] = strs[i].toCharArray();
        }
        solveSudoku(board);
    }
}