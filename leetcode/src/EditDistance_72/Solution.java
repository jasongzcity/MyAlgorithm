package EditDistance_72;

/**
 * Change String word1 to word2 using only 3 operations:
 * 1. replace a character
 * 2. delete a character
 * 3. insert a character
 *
 * return the minimum steps.
 */
public class Solution
{
    // Second session
    // dp[i, j] indicates the minimum distance between
    // w1[0:i] and w2[0:j]
    // so dp[i, j] =
    // if(w1[i]==w2[j]) dp[i-1, j-1]
    // else min(dp[i-1, j]+1 /* delete one char */
    //          dp[i, j-1]+1 /* add one char */
    //          dp[i-1, j-1]+1 /* replace one char */
    // corner cases:
    // dp[0,0] = 0
    // dp[0,j] = j; dp[i,0] = i
    public int minDistance(String word1, String word2){
        char[] a = word1.toCharArray(), b = word2.toCharArray();
        int[][] dp = new int[a.length+1][b.length+1];
        for(int i=0;i<=a.length;i++) dp[i][0] = i;
        for(int i=0;i<=b.length;i++) dp[0][i] = i;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if(a[i] == b[j]) dp[i+1][j+1] = dp[i][j];
                else
                    dp[i+1][j+1] = Math.min(dp[i][j+1],
                                    Math.min(dp[i+1][j], dp[i][j])) + 1;
            }
        }
        return dp[a.length][b.length];
    }

    public int minDistance2(String word1, String word2)
    {
        // Classic DP problem, the table[i][j]
        // stands for the minimum steps take word1[0..i-1]
        // convert to word2[0..j-1]
        int onelen = word1.length(),twolen = word2.length();
        int [][] table = new int[onelen+1][twolen+1];

        // set up corner cases.
        for(int i=0;i<=onelen;i++) table[i][0] = i;
        for(int i=0;i<=twolen;i++) table[0][i] = i;

        for (int i = 0; i < onelen; i++)
        {
            for (int j = 0; j < twolen; j++)
            {
                if(word1.charAt(i)==word2.charAt(j)) table[i+1][j+1] = table[i][j];
                else
                    table[i+1][j+1] = Math.min(Math.min(
                                        table[i][j],    // replacement
                                        table[i][j+1]   // delete one char in word1
                                        ),
                                        table[i+1][j]   // add one char to word1
                                        )+1;
            }
        }
        return table[onelen][twolen];
    }
}
