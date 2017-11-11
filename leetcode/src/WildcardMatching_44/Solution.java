package WildcardMatching_44;

/**
 * Implement wildcard pattern matching with support for '?' and '*'.
 *
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
 *
 * The matching should cover the entire input string (not partial).
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "*") → true
 * isMatch("aa", "a*") → true
 * isMatch("ab", "?*") → true
 * isMatch("aab", "c*a*b") → false
 */
public class Solution
{
    // Second session
    // DP
    // Figure out the bellman equation first:
    // try dp[i][j] as whether
    // s[1:i] matches p[1:j]
    // then dp[i][j] =
    // if s[i] = p[j]
    // dp[i-1][j-1]
    // else if p[j] = '?'
    // dp[i-1][j-1] (one char) or dp[i][j-1] (skip)
    // else if p[j] = '*'
    // dp[i][j-1] (this is skip condition)
    // or dp[i-1][j-1] (one char)
    // or dp[i-1][j]   (multiple)
    public static boolean isMatchII(String s, String p){
        char[] sa = s.toCharArray(), pa = p.toCharArray();
        boolean[][] dp = new boolean[sa.length+1][pa.length+1];
        dp[0][0] = true;
        // preprocess
        for (int i = 0; i < pa.length; i++)
            if (pa[i] == '*')
                dp[0][i+1] = dp[0][i];


        for (int i = 0; i < sa.length; i++) {
            for (int j = 0; j < pa.length; j++) {
                if (pa[j] == sa[i] || pa[j] == '?')
                    dp[i+1][j+1] = dp[i][j];
                else if (pa[j] == '*')
                    dp[i+1][j+1] = dp[i][j]||       /* one char */
                                    dp[i+1][j]||    /* no char */
                                    dp[i][j+1];     /* multiple char */
                // this is for one or zero character
//                else if (pa[j] == '?')
//                    dp[i+1][j+1] = dp[i][j] || dp[i+1][j];
            }
        }
        return dp[sa.length][pa.length];
    }

    // Two pointer solution
    public static boolean isMatchII2(String s, String p) {
        char[] sa = s.toCharArray(), pa = p.toCharArray();
        // for each: i = pointer to the string, j = ...
        // star, record down the position of star.
        // match, record down the postion where the star matches at.
        // notice that the star could match nothing.
        // so the main thought of this two pointer solution
        // is that whenever we met a character we can't match using
        // the pattern, we cover it by using the star.
        int i = 0, j = 0, star = -1, match = -1;
        while (i < sa.length) {
            if (j < pa.length && (pa[j] == '?' || pa[j] == sa[i])) {
                // matched!
                ++j;
                ++i;
            } else if (j < pa.length && pa[j] == '*') {
                // we first try to skip this star, "match nothing"
                star = j++;
                match = i;
            } else if (star > -1) {
                // still can't match, use star to "reschedule" the cover plan
                i = ++match;
                j = star + 1;
            } else return false;
        }
        // skip remaining '*' in the pattern
        while (j < pa.length && pa[j] == '*') ++j;
        return j == pa.length;
    }

    public static void main(String[] args) {
        String s = "zacabc";
        String p = "*a?bc";
        System.out.println(isMatchII2(s,p));
    }

    // O(strlen+patternlen) - not necessarily..
    public static boolean isMatch(String s, String p)
    {
        int strlen = s.length(), patternlen = p.length();
        // use asterisk to record asterisk position
        int asterisk = -1,ps = 0,pp = 0,match=0;
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
            // if we have a asterisk and the current pattern character
            // could not match string, we use the asterisk to match it.
            else if(asterisk!=-1)
            {
                ps = ++match;
                // reset pattern pointer. the pp may have advanced in error.
                pp = asterisk+1;
            }
            // Still can't match.. return false
            else return false;
        }
        while(pp<patternlen&&p.charAt(pp)=='*') pp++; // skip remaining *
        return pp==patternlen;
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
