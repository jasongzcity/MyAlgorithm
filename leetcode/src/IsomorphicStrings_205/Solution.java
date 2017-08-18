package IsomorphicStrings_205;

/**
 * Given two strings s and t, determine if they are isomorphic.
 *
 * Two strings are isomorphic if the characters in s can be replaced to get t.
 *
 * All occurrences of a character must be replaced with another character
 * while preserving the order of characters.
 * No two characters may map to the same character but a character may map to itself.
 *
 * For example,
 * Given "egg", "add", return true.
 *
 * Given "foo", "bar", return false.
 *
 * Given "paper", "title", return true.
 *
 * Note:
 * You may assume both s and t have the same length.
 */
public class Solution {
    public boolean isIsomorphic(String s, String t) {
        char[] map = new char[128];
        boolean[] set = new boolean[128];
        int len = s.length();
        for(int i=0;i<len;i++){
            char key = s.charAt(i),target = t.charAt(i);
            if(map[key]==0){
                if(set[target]) return false; // target char has been map
                else{
                    map[key] = target;
                    set[target] = true;
                }
            }
            else if(map[key]!=target) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isIsomorphic("paper","title"));
        System.out.println(s.isIsomorphic("bao","foo"));
    }
}
