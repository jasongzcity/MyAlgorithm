package FindAllAnagramsInStr_438;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a string s and a non-empty string p,
 * find all the start indices of p's anagrams in s.
 *
 * Strings consists of lowercase English letters only and
 * the length of both strings s and p will not be larger than 20,100.
 * The order of output does not matter.
 *
 * Example 1:
 * Input:
 * s: "cbaebabacd" p: "abc"
 *
 * Output:
 * [0, 6]
 *
 * Explanation:
 * The substring with start index = 0 is "cba", which is an anagram of "abc".
 * The substring with start index = 6 is "bac", which is an anagram of "abc".
 *
 * Example 2:
 * Input:
 * s: "abab" p: "ab"
 *
 * Output:
 * [0, 1, 2]
 *
 * Explanation:
 * The substring with start index = 0 is "ab", which is an anagram of "ab".
 * The substring with start index = 1 is "ba", which is an anagram of "ab".
 * The substring with start index = 2 is "ab", which is an anagram of "ab".
 */
public class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        int slen = s.length(),plen = p.length();
        List<Integer> rs = new ArrayList<>(slen/plen+1);
        // set up map
        int[] map = new int[26];
        Arrays.fill(map,-1);
        for(int i=0;i<plen;i++){
            int key = p.charAt(i)-'a';
            if(map[key]==-1) map[key] = 1;
            else map[key]++;
        }
        int begin = 0,end = begin,size = 0;
        while(end<slen&&slen-begin>=plen){
            int key = s.charAt(end)-'a';
            if(map[key]>-1){
                if(map[key]==0){
                    // char is redundant
                    while(s.charAt(begin)-'a'!=key){
                        map[s.charAt(begin)-'a']++;
                        --size;
                        ++begin;
                    }
                    ++begin;
                }else{
                    map[key]--;
                    ++size;
                }
                if(size==plen) rs.add(begin);
                ++end;
            }else{
                // char at end position is irrelevant, recover map then skip the char
                while(begin<end) map[s.charAt(begin++)-'a']++;
                size = 0;
                begin = ++end;
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findAnagrams2("cbaebabacd","abc").toString());
    }

    // most voted solution on leetcode
    // same idea but way simpler
    // notice that this solution can also be generalized to
    // any window substring questions e.g Minimum window substring(#76)
    // origin from
    // https://discuss.leetcode.com/topic/30941/here-is-a-10-line-template-that-can-solve-most-substring-problems
    public List<Integer> findAnagrams2(String s, String p){
        int slen = s.length(),plen = p.length();
        List<Integer> rs = new ArrayList<>(slen/plen+1);
        int[] map = new int[26];
        for(int i=0;i<plen;i++) map[p.charAt(i)-'a']++;
        int begin = 0,end = begin,counter = plen;
        while(end<slen){
            if(map[s.charAt(end++)-'a']-->=1) counter--;
            if(counter==0) rs.add(begin);
            if(end-begin==plen&&map[s.charAt(begin++)-'a']++>=0) ++counter;
        }
        return rs;
    }
}
