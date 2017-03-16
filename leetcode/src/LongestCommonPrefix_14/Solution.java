package LongestCommonPrefix_14;

/**
 * Write a function to find the
 * longest common prefix string
 * amongst an array of strings.
 */
public class Solution
{
    public static String longestCommonPrefix(String[] strs)
    {
        if(strs.length==0) return "";
        // loop-in-loop?
        for(int i=0;i<strs[0].length();i++)
        {
            char target = strs[0].charAt(i);
            for(int j=1;j<strs.length;j++) // check all other strings
            {
                if(i==strs[j].length()||target!=strs[j].charAt(i))
                    return strs[0].substring(0,i);
            }
        }
        return strs[0];
    }
}
