package FlipGame_294;

/**
 * You are playing the following Flip Game with your friend:
 * Given a string that contains only these two characters:
 * + and -, you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and therefore
 * the other person will be the winner.
 *
 * Write a function to determine if the starting player can guarantee a win.
 *
 * For example, given s = "++++", return true.
 * The starting player can guarantee a win by
 * flipping the middle "++" to become "+--+".
 *
 * Follow up:
 * Derive your algorithm's runtime complexity.
 */
public class Solution {
    public boolean canWin(String s) {
        return canWin(s.toCharArray());
    }

    private boolean canWin(char[] a){
        int p = 0;
        while(p<a.length-1){
            if(a[p]=='+'){
                if(a[p+1]=='+'){
                    a[p] = a[p+1] = '-';
                    boolean oppoWin = canWin(a);
                    a[p] = a[p+1] = '+';
                    if(!oppoWin) return true; // if opposite can't win, then I win.
                    ++p;
                }else p+=2;
            }else ++p;
        }
        return false;
    }

    // There is another DP solution which I can't understand....
    // https://discuss.leetcode.com/topic/
    // 27282/theory-matters-from-backtracking-128ms-to-dp-0ms/39

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.canWin("++++"));

    }
}
