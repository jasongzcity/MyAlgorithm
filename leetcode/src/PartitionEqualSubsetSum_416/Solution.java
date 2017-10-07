package PartitionEqualSubsetSum_416;

/**
 * Given a non-empty array containing only positive integers,
 * find if the array can be partitioned into two subsets such that
 * the sum of elements in both subsets is equal.
 *
 * Note:
 * Each of the array element will not exceed 100.
 * The array size will not exceed 200.
 *
 * Example 1:
 * Input: [1, 5, 11, 5]
 * Output: true
 * Explanation: The array can be partitioned as [1, 5, 5] and [11].
 *
 * Example 2:
 * Input: [1, 2, 3, 5]
 * Output: false
 * Explanation: The array cannot be partitioned into equal sum subsets.
 */
public class Solution {
    public boolean canPartition(int[] nums) {
        // input not empty, no need to check
        int sum = 0;
        for(int i:nums) sum+=i;
        if(sum%2!=0) return false;
        int[] dp = new int[sum/2];
        dp[0] = 1;
        for(int i:nums){
            if(i<=sum/2&&dp[sum/2-i]!=0) return true;
            for(int j=sum/2-1;j>=i;j--) dp[j] += dp[j-i];
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.canPartition(new int[]{1,2,3,6}));
        System.out.println(s.canPartition(new int[]{1,2,3,8}));
    }

    // 0/1 knapsack problem:
    // put items from an array into a bag.
    // And the items has the largest value.
    // transition equation:
    // f(i,v) represents the largest values we can get from previous
    // i items put in a backpack has volume v, so
    // f(i,v) = max(f(i-1,v),f(i-1,v-volumn(i))+value(i))
    // (not putting the ith item vs. putting ith item.)
    // items[i][0] is the value of the item, items[i][1] is
    // the volume of the item
    public int knapsack01(int[][] items, int V){
        if(items.length==0) return 0;
        int[] dp = new int[V+1];
        for(int i=0;i<items.length;i++)
            for(int j=V;j>=items[i][1];j--)
                dp[j] = Math.max(dp[j],dp[j-items[i][1]]+items[i][0]);
        return dp[V];
    }
}
