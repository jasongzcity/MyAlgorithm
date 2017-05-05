package BestTimeToBuyNSellII_122;

/**
 * Say you have an array for which the ith element is the price of
 * a given stock on day i.
 *
 * Design an algorithm to find the maximum profit.
 * You may complete as many transactions as you like
 * (ie, buy one and sell one share of the stock multiple times).
 * However, you may not engage in multiple transactions at the same time
 * (ie, you must sell the stock before you buy again).
 */
public class Solution {
    public static int maxProfit2(int[] prices) {
        int profit = 0,minValue = Integer.MAX_VALUE;
        boolean buyIn = false;
        for(int i=0;i<prices.length-1;i++){
            if(buyIn){
                if(prices[i]>prices[i+1]){ // sell stock
                    profit+=prices[i]-minValue;
                    buyIn = false;
                }
            }else{
                if(prices[i]<prices[i+1]){
                    minValue = prices[i];
                    buyIn = true;
                }
            }
        }
        if(buyIn) profit+=prices[prices.length-1]-minValue; // sell at last
        return profit;
    }

    // better solution
    public static int maxProfit(int[] prices){
        int profit = 0;
        for(int i=1;i<prices.length;i++)
            if(prices[i]>prices[i-1])
                profit+=prices[i]-prices[i-1];
        return profit;
    }

    public static void main(String[] args) {
        int[] a1 = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit2(a1));
    }
}
