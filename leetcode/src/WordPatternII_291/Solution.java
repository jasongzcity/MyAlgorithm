package WordPatternII_291;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a pattern and a string str, find if str follows the same pattern.
 *
 * Here follow means a full match, such that there is a bijection
 * between a letter in pattern and a non-empty substring in str.
 *
 * Examples:
 * pattern = "abab", str = "redblueredblue" should return true.
 * pattern = "aaaa", str = "asdasdasdasd" should return true.
 * pattern = "aabb", str = "xyzabcxzyabc" should return false.
 * pattern = "aabb", str = "xyzxyzabcabd" should return false.
 * Notes:
 * You may assume both pattern and str contains only lowercase letters.
 */
public class Solution {
    public boolean wordPatternMatch(String pattern, String str) {
        return helper(pattern.toCharArray(),str.toCharArray(),0,0,
                new HashMap<>(pattern.length()<<1),new HashSet<>());
    }

    private boolean helper(char[] p, char[] s, int pstart, int sstart,
                           Map<Character,String> map,Set<String> set){
        if(pstart==p.length||sstart==s.length)
            return pstart==p.length&&sstart==s.length;

        String str = map.get(p[pstart]);
        if(str!=null){
            // previously mapped
            int send = sstart+str.length(); // send exclusive
            if(compStrWithCharArray(str,s,sstart,send))
                return helper(p,s,pstart+1,send,map,set);
            else return false;
        }else{
            int send = sstart+1;
            for(;s.length-send>=p.length-1-pstart;send++){
                String key = new String(s,sstart,send-sstart);
                if(set.contains(key)) continue;
                map.put(p[pstart],key);
                set.add(key);
                if(helper(p,s,pstart+1,send,map,set)) return true;
                set.remove(key);
            }
            map.remove(p[pstart]);
            return false;
        }
    }

    // end exclusive
    private boolean compStrWithCharArray(String s,char[] ca,int begin,int end){
        for(int i=0;i<s.length()&&begin<ca.length;i++,begin++)
            if(s.charAt(i)!=ca[begin])
                return false;
        return begin==end;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.wordPatternMatch("abab","redblueredblue"));
        System.out.println(s.wordPatternMatch("abab","redblueredblu"));
        System.out.println(s.wordPatternMatch("aa","redblue"));
        System.out.println(s.wordPatternMatch("aabb","xyzxyzabcabd"));
    }
}
