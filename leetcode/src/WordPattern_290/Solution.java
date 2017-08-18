package WordPattern_290;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a pattern and a string str, find if str follows the same pattern.
 *
 * Here follow means a full match, such that there is a bijection between a letter
 * in pattern and a non-empty word in str.
 *
 * Examples:
 * pattern = "abba", str = "dog cat cat dog" should return true.
 * pattern = "abba", str = "dog cat cat fish" should return false.
 * pattern = "aaaa", str = "dog cat cat dog" should return false.
 * pattern = "abba", str = "dog dog dog dog" should return false.
 * Notes:
 * You may assume pattern contains only lowercase letters,
 * and str contains lowercase letters separated by a single space.
 */
public class Solution {
    public boolean wordPattern(String pattern, String str) {
        String[] words = str.split(" ");
        Map<String,Character> map = new HashMap<>(words.length<<1);
        boolean[] set = new boolean[26];
        if(words.length!=pattern.length()) return false;
        for(int i=0;i<words.length;i++){
            Character c = map.get(words[i]),cp = pattern.charAt(i);
            if(c==null){
                if(set[cp-'a']) return false;
                set[cp-'a'] = true;
                map.put(words[i],cp);
                continue;
            }
            if(c!=cp) return false;
        }
        return true;
    }
}
