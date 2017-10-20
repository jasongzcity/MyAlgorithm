package PermutationInString_567;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given two strings s1 and s2,
 * write a function to return true if s2 contains the permutation of s1.
 * In other words, one of the first string's permutations
 * is the substring of the second string.
 *
 * Example 1:
 * Input:s1 = "ab" s2 = "eidbaooo"
 * Output:True
 * Explanation: s2 contains one permutation of s1 ("ba").
 *
 * Example 2:
 * Input:s1= "ab" s2 = "eidboaoo"
 * Output: False
 *
 * Note:
 * The input strings only contain lower case letters.
 * The length of both given strings is in range [1, 10,000].
 */
public class Solution {
    // trivial solution: brute-force matching..
    // Queue!!!!
    public boolean checkInclusion2(String s1, String s2) {
        int[] count = new int[26];
        for(char c:s1.toCharArray()) ++count[c-'a'];
        Queue<Character> q = new LinkedList<>();
        char[] s = s2.toCharArray();
        for(int i=0;i<s.length&&s1.length()!=q.size();i++){
            char c = (char)(s[i] - 'a');
            if(count[c]>0){
                q.add(c);
                --count[c];
            }else{
                while(!q.isEmpty()&&q.peek()!=c) count[q.poll()]++;
                if(!q.isEmpty()){
                    q.add(q.poll());
                }
            }
        }
        return s1.length()==q.size();
    }

    // Check minimum window substring(#76), it's the same....
    // redo
    // better two pointer solution
    public boolean checkInclusion(String s1, String s2){
        int[] count = new int[26];
        for(char c:s1.toCharArray()) ++count[c-'a'];
        char[] s = s2.toCharArray();
        int num = s1.length();
        for(int left = 0,right = 0;right<s.length;right++){
            if(count[s[right]-'a']-->0) --num;
            while(num==0){
                if(right-left+1==s1.length()) return true;
                if(count[s[left++]-'a']++==0) ++num;
            }
        }
        return false;
    }
}
