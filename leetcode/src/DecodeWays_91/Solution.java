package DecodeWays_91;

/**
 * A message containing letters from A-Z is being encoded to numbers using
 * the following mapping:
 *
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 *
 * Given an encoded message containing digits,
 * determine the total number of ways to decode it.
 *
 * For example,
 * Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
 * The number of ways decoding "12" is 2.
 */
public class Solution {

    // Second session
    // bellman equation:
    // let dp[i] be the number of ways of coding
    // in s[0:i]
    // so dp[i] = dp[i-1]
    //            + dp[i-2] if( s(i-2,i) valid )
    public static int numDecodingsII(String s){
        int n = s.length();
        // assume input all digits..
        // work against corner cases..
        if(n==0||s.charAt(0)=='0') return 0;
        if(n==1) return 1;
        int[] dp = new int[n+1];
        dp[1] = dp[0] = 1;
        char[] ss = s.toCharArray();
        for(int i=1;i<n;i++){
            if(ss[i]!='0') dp[i+1] += dp[i];
            int tm = Integer.parseInt(s.substring(i-1,i+1));
            if(tm>=10&&tm<=26) dp[i+1] += dp[i-1];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(numDecodingsII("10"));
    }


    public static int numDecodings(String s) {
        int len = s.length();
        if(len==0||s.charAt(0)-'0'==0) return 0;
        if(len==1) return 1;
        int[] dp = new int[len];
        int temp = Integer.parseInt(s.substring(0,2));
        if(temp>20&&temp%10==0) return 0; // notice: cannot decode strings like "70".
        if(temp>26||temp%10==0){
            dp[0] = dp[1] = 1;
        }else{
            dp[0] = 1;
            dp[1] = 2;
        }
        for(int i=2;i<len;i++){
            temp = Integer.parseInt(s.substring(i-1,i+1));
            if((temp>20&&temp%10==0)||temp==0) return 0;
            if(temp>26||temp<10) dp[i] = dp[i-1];
            else if(temp%10==0)  dp[i] = dp[i-2];
            else dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[len-1];
    }

    // most voted leetcode solution
    public static int numDecodings2(String s) {
        int n = s.length();
        if (n == 0) return 0;

        int[] memo = new int[n+1];
        memo[n]  = 1;
        memo[n-1] = s.charAt(n-1) != '0' ? 1 : 0;

        for (int i = n - 2; i >= 0; i--)
            if (s.charAt(i) == '0') continue;
            else memo[i] = (Integer.parseInt(s.substring(i,i+2))<=26) ? memo[i+1]+memo[i+2] : memo[i+1];

        return memo[0];
    }
}
