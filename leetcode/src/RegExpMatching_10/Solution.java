package RegExpMatching_10;

/**
 * Implement regular expression matching with support for '.' and '*'.
 */
public class Solution
{
    /* This DP solution is inspired by the most voted solution on leetcode */
    public static boolean isMatch(String s, String p)
    {
        /**
         * Explanation :
         * 1, If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
         * 2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
         * 3, If p.charAt(j) == '*':
         * here are two sub conditions:
         * 1   if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]
         * 2   if p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.':
         * dp[i][j] = dp[i-1][j]      // in this case, a* counts as multiple a
         * or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a
         * or dp[i][j] = dp[i][j-2]   // in this case, a* counts as empty
         *
         * Also, the #44 wildcard matching could use alike solution.
         */

        int strlen = s.length(), patternlen = p.length();
        boolean[][] matchTable = new boolean[strlen+1][patternlen+1]; // all false

        // Notice: true at matchTable[i][j] means s[0..i-1] matches p[0..j-1]
        // matchTable[0][k] and matchTable[k][0] are for empty string matching.
        matchTable[0][0] = true; // matched when both are empty

        //for(int i=1;i<=strlen;i++) matchTable[i][0] = false;
        for(int i=0;i<patternlen;i++) // string is empty and patterns contain *
            if(i>0&&p.charAt(i)=='*'&&matchTable[0][i-1]) matchTable[0][i+1] = true; // matched at p[0,i]

        for(int i=0;i<strlen;i++)
        {
            for(int j=0;j<patternlen;j++)
            {
                if(p.charAt(j)==s.charAt(i)||p.charAt(j)=='.')
                    matchTable[i+1][j+1] = matchTable[i][j];// matched!
                if(p.charAt(j)=='*') // we may need to skip it or match it
                {
                    if((p.charAt(j-1)!=s.charAt(i))&&p.charAt(j-1)!='.') // skip it
                        matchTable[i+1][j+1] = matchTable[i+1][j-1];     // pass the boolean.
                    else // it would be true if one of the below position matched: single char/multiple char/skip it
                        matchTable[i+1][j+1] = matchTable[i+1][j]||matchTable[i][j+1]||matchTable[i+1][j-1];
                }
            }
        }
        return matchTable[strlen][patternlen];
    }

    public static void main(String[] args) { System.out.println(isMatch("abcd",".*")); }
 }