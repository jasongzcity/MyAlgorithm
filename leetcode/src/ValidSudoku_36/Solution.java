package ValidSudoku_36;

/**
 * Determine if a Sudoku is valid.
 *
 * The Sudoku board could be partially filled,
 * where empty cells are filled with the character '.'.
 *
 * A valid Sudoku board (partially filled) is not necessarily solvable.
 * Only the filled cells need to be validated.
 **/
public class Solution {
    public static boolean isValidSudoku(char[][] board) {
        // use 3 tables to record number appearance
        boolean[][] row = new boolean[9][9];
        boolean[][] column = new boolean[9][9];
        boolean[][] nine = new boolean[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0' - 1; // so that '1' goes to 0
                    if (row[i][num] || column[j][num] || nine[i / 3 * 3 + j / 3][num]) return false;
                    row[i][num] = column[j][num] = nine[i / 3 * 3 + j / 3][num] = true;
                }
        return true;
    }


    // below solution use bit manipulation to deal with this problem, so smart
//    bool isValidSudoku(vector<vector<char>>& board) {
//        vector<short> col(9, 0);
//        vector<short> block(9, 0);
//        vector<short> row(9, 0);
//        for (int i = 0; i < 9; i++)
//            for (int j = 0; j < 9; j++) {
//                if (board[i][j] != '.') {
//                    int idx = 1 << (board[i][j] - '0');
//                    if (row[i] & idx || col[j] & idx || block[i/3 * 3 + j / 3] & idx)
//                        return false;
//                    row[i] |= idx;
//                    col[j] |= idx;
//                    block[i/3 * 3 + j/3] |= idx;
//                }
//            }
//        return true;
//    }
}
