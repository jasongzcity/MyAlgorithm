package ValidAnagram_242;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings s and t, write a function to determine if t is an anagram of s.
 *
 * For example,
 * s = "anagram", t = "nagaram", return true.
 * s = "rat", t = "car", return false.
 *
 * Note:
 * You may assume the string contains only lowercase alphabets.
 *
 * Follow up:
 * What if the inputs contain unicode characters?
 * How would you adapt your solution to such case?
 */
public class Solution {
    public boolean isAnagram(String s, String t) {
        int slen = s.length(),tlen = t.length();
        if(slen!=tlen) return false;
        char[] ta = t.toCharArray(),sa = s.toCharArray();
        int[] map = new int[26];
        for(int i=0;i<slen;i++){
            ++map[sa[i]-'a'];
            --map[ta[i]-'a'];
        }
        for(int i=0;i<26;i++)
            if(map[i]!=0)
                return false;
        return true;
    }

    // unicode situation
    public boolean isAnagram2(String s, String t) {
        int slen = s.length(), tlen = t.length();
        if (slen != tlen) return false;
        char[] ta = t.toCharArray(), sa = s.toCharArray();
        Map<Character,Integer> map = new HashMap<>();
        for(int i=0;i<slen;i++){
            Integer tm = map.getOrDefault(ta[i],0);
            map.put(ta[i],++tm);
            tm = map.getOrDefault(sa[i],0);
            map.put(sa[i],--tm);
        }
        for(int i=0;i<slen;i++)
            if(map.get(sa[i])!=0)
                return false;
        return true;
    }
}
