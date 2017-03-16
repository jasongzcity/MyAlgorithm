package LSubstrWithoutRL_3;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 * Leetcode : Longest Substring without repeating characters.
 */
public class Solution
{
    // O(n)
    public static int lengthOfLongestSubstring(String s)
    {
        Map<Character,Integer> charMap = new HashMap<>();
        int longest = 0;
        int currentLength = 0;
        for(int i=0;i<s.length();i++)
        {
            Integer index = (Integer)charMap.get(s.charAt(i));
            if(index == null)
            {
                currentLength++;
                charMap.put(s.charAt(i),i);
            }
            else
            {
                int head = i-currentLength;
                if(currentLength>longest)
                    longest = currentLength;
                currentLength = i - index.intValue();
                charMap.replace(s.charAt(i),i);
                while(head<index.intValue()) {
                    charMap.remove(s.charAt(head));
                    head++;
                }
            }
        }
        // should check longest again
        if(currentLength>longest)
            longest = currentLength;
        return longest;
    }

    public static void main(String[] args)
    {
        String s = "asdjhuihafll";
        System.out.println(s+":"+lengthOfLongestSubstring(s));
        s = "qwyeuyuiok";
        System.out.println(s+":"+lengthOfLongestSubstring(s));
        s = "jiondqwnmvksljadiwq";
        System.out.println(s+":"+lengthOfLongestSubstring(s));

    }
}
