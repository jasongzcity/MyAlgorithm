package IsSubsequence_392;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given a string s and a string t, check if s is subsequence of t.
 *
 * You may assume that there is only lower case English letters in both s and t.
 * t is potentially a very long (length ~= 500,000) string,
 * and s is a short string (<=100).
 *
 * A subsequence of a string is a new string which is formed from
 * the original string by deleting some (can be none) of
 * the characters without disturbing the relative positions of
 * the remaining characters.
 * (ie, "ace" is a subsequence of "abcde" while "aec" is not).
 *
 * Example 1:
 * s = "abc", t = "ahbgdc"
 * Return true.
 *
 * Example 2:
 * s = "axc", t = "ahbgdc"
 * Return false.
 *
 * Follow up:
 * If there are lots of incoming S, say S1, S2, ... ,Sk
 * where k >= 1B, and you want to check one by one
 * to see if T has its subsequence.
 * In this scenario, how would you change your code?
 */
public class Solution {
    // brute force
    public boolean isSubsequence2(String s, String t) {
        char[] sa = s.toCharArray(),ta = t.toCharArray();
        int ps = 0,pt = 0;
        while(ps<sa.length&&pt<ta.length)
            if(ta[pt++]==sa[ps])
                ++ps;
        return ps==sa.length;
    }

    // Binary Search solution
    public boolean isSubsequence3(String s, String t) {
        List<Integer>[] idx = new List[256]; // Just for clarity
        for (int i = 0; i < t.length(); i++) {
            if (idx[t.charAt(i)] == null)
                idx[t.charAt(i)] = new ArrayList<>();
            idx[t.charAt(i)].add(i);
        }

        int prev = 0;
        for (int i = 0; i < s.length(); i++) {
            if (idx[s.charAt(i)] == null) return false;
            int j = Collections.binarySearch(idx[s.charAt(i)], prev);
            if (j < 0) j = -j - 1;
            if (j == idx[s.charAt(i)].size()) return false;
            prev = idx[s.charAt(i)].get(j) + 1;
        }
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isSubsequence("abc","ahbgdc"));
    }

    private static final int N = 26; // assure lower case letters

    // Binary Search solution
    @SuppressWarnings("unchecked")
    public boolean isSubsequence(String s, String t){
        List[] map = new List[N];
        for(int i=0;i<t.length();i++){
            int key = t.charAt(i)-'a';
            if(map[key]==null) map[key] = new ArrayList(t.length()/26);
            map[key].add(i); // mark down position
        }

        int prev = 0;
        for(int i=0;i<s.length();i++){
            int key = s.charAt(i)-'a';
            List<Integer> l = map[key];
            if(l==null) return false;
            int lo = 0,hi = l.size();
            // do binary search in the list for proper position
            while(lo<hi){
                int mid = lo+((hi-lo)>>>1);
                int tar = l.get(mid);
                if(prev>tar) lo = mid+1;
                else hi = mid;
            }
            if(lo==l.size()) return false;
            prev = l.get(lo)+1;
        }
        return true;
    }
}
