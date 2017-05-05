package BestTimeToBuyNSellIII_123;

/**
 * Say you have an array for which the ith element
 * is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit.
 * You may complete at most two transactions.
 *
 * Note:
 * You may not engage in multiple transactions at the same time
 * (ie, you must sell the stock before you buy again).
 */
public class Solution {
    // most voted DP solution in leetcode
    // can be generalized to k transactions
    // O(kn)
    public static int maxProfit(int[] p,int k) {
        // f[k, ii] represents the max profit up until prices[ii]
        // (Note: NOT ending with prices[ii]) using at most k transactions.
        // f[k, ii] = max(f[k, ii-1], prices[ii] - prices[jj] + f[k-1, jj])
        // { jj in range of [0, ii-1] }
        //          = max(f[k, ii-1], prices[ii] + max(f[k-1, jj] - prices[jj]))
        // f[0, ii] = 0; 0 times transation makes 0 profit
        // f[k, 0] = 0; if there is only one price data point you can't
        // make any money no matter how many times you can trade

        // Transition equation:
        // f[k, ii] = max(f[k, ii-1], prices[ii] - prices[jj] + f[k-1, jj]) { jj in range of [0, ii-1] }
        //          = max(f[k, ii-1], prices[ii] + max(f[k-1, jj] - prices[jj]))
        int len = p.length;
        if(len<=1) return 0;
        int[][] dp = new int[k+1][len];
        for(int i=1;i<=k;i++){
            int lastMax = dp[i-1][0]-p[0];
            for(int j=1;j<len;j++){
                dp[i][j] = Math.max(dp[i][j-1],p[j]+lastMax);
                lastMax = Math.max(lastMax,dp[i-1][j]-p[j]); // update for next loop
            }
        }
        return dp[k][len-1];
    }

    public static int maxProfit(int[] p){
        return maxProfit(p,2);
    }

    public static void main(String[] args) {
        int[] prices = new int[]{7,1,5,3,6,4,8,9,3,6};
//        System.out.println(maxProfit(prices,8));
        System.out.println(maxProfit2(prices));
    }

    // most voted and one pass solution on leetcode
    public static int maxProfit2(int[] prices) {
        int hold1 = Integer.MIN_VALUE, hold2 = Integer.MIN_VALUE;
        int release1 = 0, release2 = 0;
        for(int i:prices){
            release2 = Math.max(release2,hold2+i);
            hold2 = Math.max(hold2,release1-i);
            release1 = Math.max(release1,hold1+i);
            hold1 = Math.max(hold1,-i);
        }
        return release2;
    }
}
