package TargetSum_494;

import java.util.Arrays;

/**
 * You are given a list of non-negative integers, a1, a2, ...,
 * an, and a target, S. Now you have 2 symbols + and -.
 * For each integer, you should choose one from + and - as its new symbol.
 *
 * Find out how many ways to assign symbols to make sum of integers equal to target S.
 *
 * Example 1:
 * Input: nums is [1, 1, 1, 1, 1], S is 3.
 * Output: 5
 * Explanation:
 * -1+1+1+1+1 = 3
 * +1-1+1+1+1 = 3
 * +1+1-1+1+1 = 3
 * +1+1+1-1+1 = 3
 * +1+1+1+1-1 = 3
 * There are 5 ways to assign symbols to make the sum of nums be target 3.
 *
 * Note:
 * The length of the given array is positive and will not exceed 20.
 * The sum of elements in the given array will not exceed 1000.
 * Your output answer is guaranteed to be fitted in a 32-bit integer.
 */
public class Solution {
    // brute force cost(2^n)

    // solution from leetcode
    // DFS with memoization
    public int findTargetSumWays(int[] nums, int S) {
        int[][] memo = new int[nums.length][2001];
        for (int[] row: memo)
            Arrays.fill(row, Integer.MIN_VALUE);
        return calculate(nums, 0, 0, S, memo);
    }

    public int calculate(int[] nums, int i, int sum, int S, int[][] memo) {
        if (i == nums.length) {
            if (sum == S)
                return 1;
            else
                return 0;
        } else {
            if (memo[i][sum + 1000] != Integer.MIN_VALUE) {
                return memo[i][sum + 1000];
            }
            int add = calculate(nums, i + 1, sum + nums[i], S, memo);
            int subtract = calculate(nums, i + 1, sum - nums[i], S, memo);
            memo[i][sum + 1000] = add + subtract;
            return memo[i][sum + 1000];
        }
    }

    // Do it myself
    // what is recalculated in the brute force solution?
    // the specific sum when comes to certain position.
    public int findTargetSumWays2(int[] nums, int S){
        if(nums.length==0) return 0;
        int[][] memo = new int[nums.length][2001];
        for(int[] arr:memo) Arrays.fill(arr, Integer.MIN_VALUE);
        return dfs(nums, S, 0, 0, memo);
    }

    private int dfs(int[] a, int tar, int pos, int sum, int[][] memo){
        if(pos==a.length) return sum==tar?1:0;
        if(memo[pos][sum+1000]!=Integer.MIN_VALUE) return memo[pos][sum+1000];
        int add = dfs(a, tar, pos+1, sum+a[pos], memo);
        int minus = dfs(a, tar, pos+1, sum-a[pos], memo);
        memo[pos][sum+1000] = add+minus;
        return add+minus;
    }

    // DP solution
    public int findTargetSumWays3(int[] nums, int tar){
        if(nums.length==0||tar>1000) return 0;
        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0]+1000] += 1;
        dp[0][-nums[0]+1000] += 1;
        for(int i=1;i<nums.length;i++){
            for(int j=-1000;j<=1000;j++){
                if(dp[i-1][j+1000]>0){
                    dp[i][j+1000+nums[i]] += dp[i-1][j+1000];
                    dp[i][j+1000-nums[i]] += dp[i-1][j+1000];
                }
            }
        }
        return dp[nums.length-1][tar+1000];
    }

    // A even simpler way:
    // explanation:
    // https://discuss.leetcode.com/topic/76243/
    // java-15-ms-c-3-ms-o-ns-iterative-dp-solution-using-subset-sum-with-explanation
    // transfer the question to finding number of subsets that
    // sums up = (sum + target)/2
    public int findTargetSumWays4(int[] nums, int tar){
        if(nums.length==0||tar>1000) return 0;
        int sum = 0;
        for(int i:nums) sum+=i;
        return (sum + tar) % 2 == 0 ? subsetNum(nums, (sum + tar) / 2) : 0 ;
    }

    // return number of subsets in array a which has sum = tar
    // classic subset sum dp
    // brute force takes O(2^N)
    private int subsetNum(int[] a, int tar){
        int[] dp = new int[tar+1];
        dp[0] = 1;
        for(int i:a){
            for(int j=tar;j>=i;j--){
                dp[j] += dp[j-i];
            }
        }
        return dp[tar];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] a = {1,2,4,5,7,8};
        System.out.println(s.subsetNum(a, 7));
    }
}
