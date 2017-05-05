package BestTimeToBuyNSellIV_188;

/**
 * Say you have an array for which the ith element
 * is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit.
 * You may complete at most k transactions.
 *
 * Note:
 * You may not engage in multiple transactions at the same time
 * (ie, you must sell the stock before you buy again).
 */
public class Solution {
    // This problem have been considered at III(#123)
    // The only difference is that leetcode OJ
    // have some extreme inputs: very large k
    // So, we have to change it to O(n) space complexity
    // and return directly when no more profit can be achieved
    public static int maxProfit(int k,int[] p){
        int len = p.length;
        if(len==0) return 0;
        int[] dp = new int[len];
        if(k>=len>>1){ // special case to deal with large k
            int profit = 0;
            for(int i=1;i<len;i++)
                if(p[i]>p[i-1])
                    profit+=p[i]-p[i-1];
            return profit;
        }
        for(int i=0;i<k;i++){
            int lastMax = -p[0];
            for(int j=1;j<len;j++){
                int last = dp[j]; // backup
                dp[j] = Math.max(dp[j-1],p[j]+lastMax);
                lastMax = Math.max(last-p[j],lastMax);
            }
        }
        return dp[len-1];
    }

    public static void main(String[] args) {
        int[] prices = new int[]{7,1,5,3,6,4,8,9,3,6};
        System.out.println(maxProfit(100000,prices));
    }
}
