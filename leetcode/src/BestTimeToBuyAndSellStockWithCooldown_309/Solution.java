package BestTimeToBuyAndSellStockWithCooldown_309;

/**
 * Say you have an array for which the ith element is the price
 * of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit.
 * You may complete as many transactions as you like
 * (ie, buy one and sell one share of the stock multiple times)
 * with the following restrictions:
 * You may not engage in multiple transactions at the same time
 * (ie, you must sell the stock before you buy again).
 * After you sell your stock, you cannot buy stock on next day.
 * (ie, cooldown 1 day)
 *
 * Example:
 * prices = [1, 2, 3, 0, 2]
 * maxProfit = 3
 * transactions = [buy, sell, cooldown, buy, sell]
 */
public class Solution {

    // Second session, do it in O(1) space
    // accepted..
    public int maxProfit(int[] p){
        if(p.length==0) return 0;
        int sell = 0, preSell = 0, buy = -p[0];
        for(int i=1;i<p.length;i++){
            int tm = sell;
            sell = Math.max(sell, p[i]+buy);
            buy = Math.max(buy, preSell-p[i] /* sell it the day before
                                                yesterday and selling it now */
            );
            preSell = tm;
        }
        return sell;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maxProfit(new int[]{1,2,3,1,3}));
    }

    // naive O(n) space dp solution
    // we can improve it to O(1) space
    public int maxProfit2(int[] prices) {
        if(prices.length<2) return 0;
        int[] buy = new int[prices.length], sell = new int[prices.length];
        buy[0] = -prices[0];
        for(int i=1;i<prices.length;i++){
            sell[i] = Math.max(prices[i] + buy[i-1], sell[i-1]);
            buy[i] = i-2>=0?Math.max(sell[i-2]-prices[i], buy[i-1]):
                    Math.max(buy[i-1],-prices[i]);
        }
        return sell[prices.length-1];
    }
}
