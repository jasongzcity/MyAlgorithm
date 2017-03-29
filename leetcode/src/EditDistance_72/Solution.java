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
    public int minDistance(String word1, String word2)
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
