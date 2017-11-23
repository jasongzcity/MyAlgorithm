package DecodeWaysII_639;

/**
 * A message containing letters from A-Z is being encoded to
 * numbers using the following mapping way:
 *
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Beyond that, now the encoded string can also contain the character '*',
 * which can be treated as one of the numbers from 1 to 9.
 *
 * Given the encoded message containing digits and the character '*',
 * return the total number of ways to decode it.
 *
 * Also, since the answer may be very large, you should return the output mod 10^9 + 7.
 *
 * Example 1:
 * Input: "*"
 * Output: 9
 * Explanation: The encoded message can be decoded to the string:
 * "A", "B", "C", "D", "E", "F", "G", "H", "I".
 *
 * Example 2:
 * Input: "1*"
 * Output: 9 + 9 = 18
 * Note:
 * The length of the input string will fit in range [1, 10^5].
 * The input string will only contain the character '*' and digits '0' - '9'.
 */
public class Solution {
    // follow up of #91 - Decode Ways
    // Still DP with modifications
    // let dp[i] represents the number of
    // decode ways of dp[0:i-1]
    // notice the boundary workaround at decode ways(#91)
    // doesn't work here. we can't use boundary dp[0] = 1 here.
    public int numDecodings(String s) {
        // notice!
        // '*' = '1' - '9' not '0' -'9'
        if (s.length() == 0 || s.charAt(0) == '0') return 0;
        int[] dp = new int[s.length() + 1];
        int divider = 1000000007;

        char[] ss = s.toCharArray();
        dp[0] = 1;
        if (ss[0] == '*') dp[1] = 9;
        else dp[1] = 1;

        for (int i = 1; i < ss.length; i++) {
            if (ss[i] == '*') {
                dp[i + 1] = (dp[i + 1] + dp[i] * 9);
                if (ss[i - 1] == '*') dp[i + 1] = (dp[i + 1] + dp[i - 1] * 15) % divider;
                else if (ss[i - 1] == '2') dp[i + 1] = (dp[i + 1] + dp[i - 1] * 6) % divider;
                else if (ss[i - 1] == '1') dp[i + 1] = (dp[i + 1] + dp[i - 1] * 9) % divider;
                // ss[i-1] == '0', invalid
            } else if (ss[i] == '0') {
                if (ss[i - 1] == '*') dp[i + 1] = (dp[i + 1] + dp[i - 1] * 2) % divider;
                else if (ss[i - 1] == '1' || ss[i - 1] == '2') dp[i + 1] = (dp[i + 1] + dp[i - 1]) % divider;
                else return 0; // invalid
            } else if (ss[i] <= '6') {
                dp[i + 1] = (dp[i + 1] + dp[i]);
                if (ss[i - 1] == '*') dp[i + 1] = (dp[i + 1] + dp[i - 1] * 2) % divider; // 1? or 2?
                else if (ss[i - 1] == '1' || ss[i - 1] == '2') dp[i + 1] = (dp[i + 1] + dp[i - 1]) % divider;
            } else {
                // 7 8 9
                dp[i + 1] = (dp[i + 1] + dp[i]);
                if (ss[i - 1] == '*' || ss[i - 1] == '1')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % divider; // 1?
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numDecodings("**"));
        System.out.println(881150112%1000000007);
    }

    // Accepted solution from leetcode
    int M = 1000000007;
    public int numDecodings2(String s) {
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : s.charAt(0) == '0' ? 0 : 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                dp[i + 1] = 9 * dp[i];
                if (s.charAt(i - 1) == '1')
                    dp[i + 1] = (dp[i + 1] + 9 * dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '2')
                    dp[i + 1] = (dp[i + 1] + 6 * dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '*')
                    dp[i + 1] = (dp[i + 1] + 15 * dp[i - 1]) % M;
            } else {
                dp[i + 1] = s.charAt(i) != '0' ? dp[i] : 0;
                if (s.charAt(i - 1) == '1')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
                    dp[i + 1] = (dp[i + 1] + dp[i - 1]) % M;
                else if (s.charAt(i - 1) == '*')
                    dp[i + 1] = (dp[i + 1] + (s.charAt(i) <= '6' ? 2 : 1) * dp[i - 1]) % M;
            }
        }
        return (int) dp[s.length()];
    }
}