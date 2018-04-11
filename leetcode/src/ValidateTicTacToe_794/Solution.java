package ValidateTicTacToe_794;

//A Tic-Tac-Toe board is given as a string array board. Return True if and
// only if it is possible to reach this board position during the course of a valid tic-tac-toe game.
//
// The board is a 3 x 3 array, and consists of characters " ", "X", and "O".
// The " " character represents an empty square.
//
//        Here are the rules of Tic-Tac-Toe:
//
//        Players take turns placing characters into empty squares (" ").
//        The first player always places "X" characters, while the second player always places "O" characters.
//        "X" and "O" characters are always placed into empty squares, never filled ones.
//        The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
//        The game also ends if all squares are non-empty.
//        No more moves can be played if the game is over.
//
//        Example 1:
//        Input: board = ["O  ", "   ", "   "]
//        Output: false
//        Explanation: The first player always plays "X".
//
//        Example 2:
//        Input: board = ["XOX", " X ", "   "]
//        Output: false
//        Explanation: Players take turns making moves.
//
//        Example 3:
//        Input: board = ["XXX", "   ", "OOO"]
//        Output: false
//
//        Example 4:
//        Input: board = ["XOX", "O O", "XOX"]
//        Output: true
//
//        Note:
//
//        board is a length-3 array of strings, where each string board[i] has length 3.
//        Each board[i][j] is a character in the set {" ", "X", "O"}.


public class Solution {
    public boolean validTicTacToe(String[] board) {
        int[] validation = new int[8];

        int xcount = 0, ocount = 0;
        boolean[] winArr = new boolean[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char c = board[i].charAt(j);
                if (c != ' ') {
                    if (c == 'X') xcount++;
                    else ocount++;
                    if (i == j) testAndSet(validation, 6, winArr, c == 'X');

                    if (i + j == 2) testAndSet(validation, 7, winArr, c == 'X');

                    testAndSet(validation, i, winArr, c == 'X');
                    testAndSet(validation, 3 + j, winArr, c == 'X');
                }
            }
        }

        if (winArr[0] && winArr[1]) return false;
        else if (!(winArr[0] || winArr[1])) return xcount == ocount || xcount == ocount + 1;
        else if (winArr[0]) return xcount == ocount + 1;
        else return xcount == ocount;
    }

    void testAndSet(int[] validation, int index, boolean[] winArr, boolean plus) {
        if (plus && validation[index] >= 0) {
            if (++validation[index] == 3) {
                winArr[0] = true;
            }
        } else if (!plus && validation[index] <= 0) {
            if (--validation[index] == -3) {
                winArr[1] = true;
            }
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.validTicTacToe(new String[]{"O  ", "   ", "  X"}));
        System.out.println(s.validTicTacToe(new String[]{"O  ", "   ", "   "}));
        System.out.println(s.validTicTacToe(new String[]{"O  ", "   ", " XX"}));
        System.out.println(s.validTicTacToe(new String[]{"OOO", "   ", "XXX"}));
        System.out.println(s.validTicTacToe(new String[]{"   ", "   ", "   "}));
    }
}
