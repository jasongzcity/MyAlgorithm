package ValidWordSquare_422;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given a sequence of words, check whether it forms a valid word square.
 *
 * A sequence of words forms a valid word square if the kth row and column read
 * the exact same string, where 0 ≤ k < max(numRows, numColumns).
 *
 * Note:
 * The number of words given is at least 1 and does not exceed 500.
 * Word length will be at least 1 and does not exceed 500.
 * Each word contains only lowercase English alphabet a-z.
 *
 * Example 1:
 * Input:
 * [
 *   "abcd",
 *   "bnrt",
 *   "crmy",
 *   "dtye"
 * ]
 * Output:
 * true
 *
 * Explanation:
 * The first row and first column both read "abcd".
 * The second row and second column both read "bnrt".
 * The third row and third column both read "crmy".
 * The fourth row and fourth column both read "dtye".
 * Therefore, it is a valid word square.
 *
 * Example 2:
 *
 * Input:
 * [
 *   "abcd",
 *   "bnrt",
 *   "crm",
 *   "dt"
 * ]
 * Output:
 * true
 *
 * Explanation:
 * The first row and first column both read "abcd".
 * The second row and second column both read "bnrt".
 * The third row and third column both read "crm".
 * The fourth row and fourth column both read "dt".
 * Therefore, it is a valid word square.
 *
 * Example 3:
 * Input:
 * [
 *  "ball",
 *  "area",
 *  "read",
 *  "lady"
 * ]
 * Output:
 * false
 *
 * Explanation:
 * The third row reads "read" while the third column reads "lead".
 * Therefore, it is NOT a valid word square.
 */
public class Solution {
    // change it to char matrix
    public boolean validWordSquare(List<String> words) {
        char[][] cm = new char[words.size()][];
        for(int i=0;i<words.size();i++) cm[i] = words.get(i).toCharArray();
        for(int i=0;i<cm.length;i++){
            int j=i+1;
            for(;j<cm[i].length;j++)
                if(j>=cm.length||i>=cm[j].length||cm[i][j]!=cm[j][i])
                    return false;
            if(j<cm.length&&i<cm[j].length) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        List<String> l1 = Arrays.asList("abc","bde","cefg");
        List<String> l2 = Arrays.asList("abc","bde","cef");
        List<String> l3 = Arrays.asList("abc","bde","cef","z");
        List<String> l4 = Arrays.asList("abcz","bde","cef","z");
        List<String> l5 = Arrays.asList("abcz","bdekk","cef","zk");
        System.out.println(s.validWordSquare(l1));
        System.out.println(s.validWordSquare(l2));
        System.out.println(s.validWordSquare(l3));
        System.out.println(s.validWordSquare(l4));
        System.out.println(s.validWordSquare(l5));
    }
}
