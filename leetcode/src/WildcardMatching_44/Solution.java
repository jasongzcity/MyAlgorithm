package WildcardMatching_44;

/**
 * Implement wildcard pattern matching with support for '?' and '*'.
 *
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
 */
public class Solution
{
    // O(strlen+patternlen)
    public static boolean isMatch(String s, String p)
    {
        int strlen = s.length(), patternlen = p.length();
        int asterisk = -1,ps = 0,pp = 0,match=0; // use asterisk to record asterisk position
        while(ps<strlen)
        {
            if(pp<patternlen&&(s.charAt(ps)==p.charAt(pp)||p.charAt(pp)=='?'))
            {
                pp++;
                ps++;
            }
            else if(pp<patternlen&&p.charAt(pp)=='*') // found new *.
            {
                asterisk = pp;
                match = ps;
                pp++;
            }
            else if(asterisk!=-1) // if we have a asterisk and the current pattern character
            {                     // could not match string, we use the asterisk to match it.
                ps = ++match;
                pp = asterisk+1;  // reset pattern pointer. the pp may have advanced in error.
            }
            else return false;    // Still can't match.. return false
        }
        while(pp<patternlen&&p.charAt(pp)=='*') pp++; // skip remaining *
        return pp==patternlen;
    }

    public static void main(String[] args)
    {
        String s = "aaaa";
        String p = "*a";
        System.out.println(isMatch(s,p));
    }

    // O(strlen*patternlen)
    public static boolean isMatchDP(String str,String pattern)
    {
        int strlen = str.length(), patternlen = pattern.length();
        boolean [][] table = new boolean[strlen+1][patternlen+1];

        // corner cases when one or both of text and pattern are empty
        table[0][0] = true;
        //for(int temp=1;temp<=strlen;temp++) table[temp][0] = false;
        for(int temp=0;temp<patternlen;temp++)
            if(pattern.charAt(temp)=='*') table[0][temp+1] = table[0][temp];

        // construct the matrix
        for(int sp=0;sp<strlen;sp++)
        {
            for(int pp=0;pp<patternlen;pp++)
            {
                if(str.charAt(sp)==pattern.charAt(pp)||pattern.charAt(pp)=='?') // matched
                    table[sp+1][pp+1] = table[sp][pp];
                else if(pattern.charAt(pp)=='*')         // Either:
                    table[sp+1][pp+1] = table[sp][pp+1]||// match multiple char in text string
                                        table[sp][pp]||  // or match one char in text
                                        table[sp+1][pp]; // or just skip it
            }
        }
        return table[strlen][patternlen];
    }
}
