package KeyboardRow_500;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given a List of words, return the words that can be typed
 * using letters of alphabet on only one row's of American keyboard.
 *
 * Example 1:
 * Input: ["Hello", "Alaska", "Dad", "Peace"]
 * Output: ["Alaska", "Dad"]
 * Note:
 * You may use one character in the keyboard more than once.
 * You may assume the input string will only contain letters of alphabet.
 */
public class Solution {
    private int[] set = new int[26];

    public String[] findWords(String[] words) {
        // construct set.
        buildSet(set);
        List<String> l = new ArrayList<>();
        Collections.addAll(l, words);
        for(int i=0;i<l.size();i++){
            String s = l.get(i);
            char[] ca = s.toCharArray();
            int row = set[ca[0]<='Z'?ca[0]-'A':ca[0]-'a'];
            for(char cc:ca){
                int p = (cc<='Z'?cc-'A':cc-'a');
                if(set[p]!=row){
                    l.remove(i--);
                    break;
                }
            }
        }
        String[] sa = new String[l.size()];
        l.toArray(sa);
        return sa;
    }

    private void buildSet(int[] set){
        // let compiler do its job lolll
        set['q'-'a'] = 1;
        set['w'-'a'] = 1;
        set['e'-'a'] = 1;
        set['r'-'a'] = 1;
        set['t'-'a'] = 1;
        set['y'-'a'] = 1;
        set['u'-'a'] = 1;
        set['i'-'a'] = 1;
        set['o'-'a'] = 1;
        set['p'-'a'] = 1;
        set['a'-'a'] = 2;
        set['s'-'a'] = 2;
        set['d'-'a'] = 2;
        set['f'-'a'] = 2;
        set['g'-'a'] = 2;
        set['h'-'a'] = 2;
        set['j'-'a'] = 2;
        set['k'-'a'] = 2;
        set['l'-'a'] = 2;
        set['m'-'a'] = 3;
        set['n'-'a'] = 3;
        set['b'-'a'] = 3;
        set['v'-'a'] = 3;
        set['c'-'a'] = 3;
        set['x'-'a'] = 3;
        set['z'-'a'] = 3;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.toString(s.findWords(
                new String[]{"Hello", "Alaska", "Dad", "Peace"})));
    }
}
