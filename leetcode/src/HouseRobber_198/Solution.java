package HouseRobber_198;

/**
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing
 * each of them is that adjacent houses have security system connected and
 * it will automatically contact the police
 * if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers
 * representing the amount of money of each house,
 * determine the maximum amount of money you can rob tonight
 * without alerting the police.
 */
public class Solution {
    // dp solution
    public int rob2(int[] a) {
        int len = a.length,max = Integer.MIN_VALUE;
        if(len==0) return 0;
        if(len==1) return a[0];
        int[] dp = new int[len];
        dp[0] = a[0];
        dp[1] = a[1];
        if(len>2) dp[2] = a[0]+a[2];
        for(int i=3;i<len;i++) dp[i] = Math.max(dp[i-2],dp[i-3])+a[i];
        return dp[len-1]>dp[len-2]?dp[len-1]:dp[len-2];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = new int[]{3,5,8,1,3,18,4,7};
        System.out.println(s.rob(a));
    }

    // most voted solution on leetcode
    // notice its similarity to maximum product subarray(#152)
    // prevno means we didn't rob the previous house
    // prevyes means we did.
    public int rob(int[] num) {
        int prevNo = 0;
        int prevYes = 0;
        for (int n : num) {
            int temp = prevNo;
            prevNo = Math.max(prevNo, prevYes);
            prevYes = n + temp;
        }
        return Math.max(prevNo, prevYes);
    }
}
