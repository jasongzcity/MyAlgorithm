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
        Solution s = new Solution();
        int[] prices = new int[]{3,2,6,5,4,0,2};
        System.out.println(s.maxProfit2(2,prices));
    }

    // Second session
    // try to do it in a naive O(p.length*l) space way
    public int maxProfit2(int k,int[] p){
        if(p.length<2||k==0) return 0;
        if(k>=p.length>>1){ // special case to deal with large k
            int profit = 0;
            for(int i=1;i<p.length;i++)
                if(p[i]>p[i-1])
                    profit+=p[i]-p[i-1];
            return profit;
        }
        int[][] dp = new int[k][p.length];

        for(int i=0;i<k;i++){
            int largestBuy = -p[0]; /* for ith transaction we maintain a value to represent lowest buying! */
            for(int j=1;j<p.length;j++){
                int prev = i>0?dp[i-1][j]:0;
                // update the selling income
                dp[i][j] = Math.max(dp[i][j-1]          /* we don't sell now */
                                    ,p[j]+largestBuy    /* sell now */);
                // update if we buy now
                // the variable "prev" contains the largest amount of money
                // we can gain by today in i-1 transactions.
                // now we are buying one more time
                largestBuy = Math.max(largestBuy, prev-p[j]);
            }
        }
        return dp[k-1][p.length-1];
    }

    // We can improve it to O(p.length) space actually.
}
