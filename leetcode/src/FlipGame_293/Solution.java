package FlipGame_293;

import java.util.ArrayList;
import java.util.List;

/**
 * You are playing the following Flip Game with your friend:
 * Given a string that contains only these two characters: + and -,
 * you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and
 * therefore the other person will be the winner.
 *
 * Write a function to compute all possible states of the string
 * after one valid move.
 *
 * For example, given s = "++++", after one move,
 * it may become one of the following states:
 * [
 *   "--++",
 *   "+--+",
 *   "++--"
 * ]
 * If there is no valid move, return an empty list [].
 */
public class Solution {
    public List<String> generatePossibleNextMoves(String s) {
        List<String> l = new ArrayList<>(s.length());
        char[] ca = s.toCharArray();
        int p = 0;
        while(p<ca.length-1){
            if(ca[p]=='+'){
                if(ca[p+1]=='+'){
                    ca[p] = ca[p+1] = '-';
                    l.add(new String(ca));
                    ca[p] = ca[p+1] = '+';
                    ++p;
                }else p+=2;
            }else ++p;
        }
        return l;
    }
}
