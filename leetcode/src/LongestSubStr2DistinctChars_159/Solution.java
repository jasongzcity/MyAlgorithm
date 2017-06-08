package LongestSubStr2DistinctChars_159;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string, find the length of the longest substring T that
 * contains at most 2 distinct characters.
 *
 * For example, Given s = “eceba”, T is "ece" which its length is 3.
 */
public class Solution {
    public int lengthOfLongestSubstringTwoDistinct2(String s){
        int begin = 0,end = 0,len = s.length(),maxLength = 0;
        Map<Character,Integer> mapping = new HashMap<>();
        while(true){
            while(end<len&&mapping.size()<3){
                char c = s.charAt(end++);
                Integer i = mapping.getOrDefault(c,0);
                mapping.put(c,++i);
            }
            // out of bound or we have the third character.
            // now calculate the length
            if(mapping.size()==3) maxLength = Math.max(maxLength,end-1-begin);
            else maxLength = Math.max(maxLength,end-begin);
            if(end==len) break;
            while(mapping.size()==3){ // shrink the window
                char c = s.charAt(begin++);
                Integer i = mapping.get(c);
                if(i==1) mapping.remove(c);
                else mapping.put(c,--i);
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.lengthOfLongestSubstringKDistinct("abcabcabc",3));
    }

    // small improvement, using array as map
    public int lengthOfLongestSubstringTwoDistinct(String s){
        int begin = 0,end = 0,len = s.length(),maxLength = 0,size = 0;
        int[] mapping = new int[128];
        while(true){
            while(end<len&&size<3){
                char c = s.charAt(end++);
                if(mapping[c]++==0) ++size;
            }
            // out of bound or we have the third character.
            // now calculate the length
            if(size==3) maxLength = Math.max(maxLength,end-1-begin);
            else maxLength = Math.max(maxLength,end-begin);
            if(end==len) break;
            while(size==3){ // shrink the window
                char c = s.charAt(begin++);
                if(mapping[c]--==1) --size;
            }
        }
        return maxLength;
    }

    public int lengthOfLongestSubstringKDistinct(String s, int k){
        int begin = 0,end = 0,len = s.length(),maxLength = 0,size = 0;
        int[] mapping = new int[128];
        while(true){
            while(end<len&&size<=k){
                char c = s.charAt(end++);
                if(mapping[c]==0) ++size;
                mapping[c]++;
            }
            // out of bound or we have the third character.
            // now calculate the length
            if(size>k) maxLength = Math.max(maxLength,end-1-begin);
            else maxLength = Math.max(maxLength,end-begin);
            if(end==len) break;
            while(size>k){ // shrink the window
                char c = s.charAt(begin++);
                if(mapping[c]==1) --size;
                mapping[c]--;
            }
        }
        return maxLength;
    }
}
