package ImpStrStr_28;

/**
 * Implement strStr().
 *
 * Returns the index of the first occurrence of needle in haystack,
 * or -1 if needle is not part of haystack.
 */
public class Solution
{
    public static int strStr(String haystack, String needle)
    {
        if(needle.length()==0) return 0;
        for(int i=0;i<haystack.length();i++)
        {
            if(haystack.length()-i<needle.length()) return -1;// no more searching
            if(haystack.charAt(i)==needle.charAt(0))
            {
                int hp=i+1,np=1;
                for(;np<needle.length();hp++,np++)
                {
                    if(hp==haystack.length()) return -1;
                    if(haystack.charAt(hp)!=needle.charAt(np)) break;
                }
                if(np==needle.length()) return i;
            }
        }
        return -1; // haystack.length=0
    }

    // cleaner solution from leetcode discussion
//    public int strStr(String haystack, String needle) {
//        for (int i = 0; ; i++) {
//            for (int j = 0; ; j++) {
//                if (j == needle.length()) return i;
//                if (i + j == haystack.length()) return -1;
//                if (needle.charAt(j) != haystack.charAt(i + j)) break;
//            }
//        }
//    }
}
