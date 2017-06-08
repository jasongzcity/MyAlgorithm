package FirstUniqueCharInStr_387;

import java.util.Arrays;

/**
 * Given a string, find the first non-repeating character in it and return it's index.
 * If it doesn't exist, return -1.
 *
 * Examples:
 *
 * s = "leetcode"
 * return 0.
 *
 * s = "loveleetcode",
 * return 2.
 *
 * Note: You may assume the string contain only lowercase letters.
 */
public class Solution {
    public int firstUniqChar2(String s) {
        int[] map = new int[26];
        int len = s.length();
        char[] cc = s.toCharArray();
        Arrays.fill(map,-1);
        for(int i=0;i<len;i++){
            int key = cc[i]-'a';
            if(map[key]==-1) map[key] = i;
            else map[key] = -2;
        }
        int min = Integer.MAX_VALUE;
        for(int i=0;i<map.length;i++){
            if(map[i]>-1&&min>map[i])
                min = map[i];
        }
        return min==Integer.MAX_VALUE?-1:min;
    }

    // most voted solution
    // I am just overthinking.......
    public int firstUniqChar(String s) {
        int len = s.length();
        char[] cc = s.toCharArray();
        int[] map = new int[26];
        for(int i=0;i<len;i++) map[cc[i]-'a']++;
        for(int i=0;i<len;i++)
            if(map[cc[i]-'a']==1)
                return i;
        return -1;
    }

    // two pointer
    public int firstUniqChar3(String s){
        int left = 0,right = 1,len = s.length();
        if(len==0) return -1;
        int[] map = new int[26];
        char back = s.charAt(left);
        ++map[back-'a'];
        while(true){
            while(right<len&&s.charAt(right)!=back) map[s.charAt(right++)-'a']++;
            if(right==len) return left;
            ++map[s.charAt(right)-'a'];
            while(left<len&&map[s.charAt(left)-'a']>1) ++left;
            if(left==len) return -1;
            back = s.charAt(left);
            if(map[back-'a']==0){
                right = left+1;
                ++map[back-'a'];
            }
            else ++right;
        }
    }

    // improved two pointer
    public int firstUniqChar4(String s) {
        int len = s.length();
        if (len == 1) return 0;
        if (len == 0) return -1;
        int slow = 0, fast = 1;
        int[] count = new int[26];
        count[s.charAt(slow) - 'a']++;
        while (fast < len) {
            count[s.charAt(fast) - 'a']++;
            while (slow < len && count[s.charAt(slow) - 'a'] > 1) slow++;
            if (slow == len) return -1;
            if (count[s.charAt(slow) - 'a'] == 0) {
                count[s.charAt(slow) - 'a']++;
                fast = slow;
            }
            fast++;
        }
        return slow;
    }
}
