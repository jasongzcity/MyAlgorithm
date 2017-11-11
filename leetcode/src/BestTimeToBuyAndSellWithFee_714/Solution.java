package BestTimeToBuyAndSellWithFee_714;

/**
 * Your are given an array of integers prices,
 * for which the i-th element is the price of a given stock on day i;
 * and a non-negative integer fee representing a transaction fee.
 *
 * You may complete as many transactions as you like,
 * but you need to pay the transaction fee for each transaction.
 * You may not buy more than 1 share of a stock at a time
 * (ie. you must sell the stock share before you buy again.)
 *
 * Return the maximum profit you can make.
 *
 * Example 1:
 * Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
 * Output: 8
 * Explanation: The maximum profit can be achieved by:
 *
 * Buying at prices[0] = 1
 * Selling at prices[3] = 8
 * Buying at prices[4] = 4
 * Selling at prices[5] = 9
 * The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
 * Note:
 *
 * 0 < prices.length <= 50000.
 * 0 < prices[i] < 50000.
 * 0 <= fee < 50000.
 */
public class Solution {
    public int maxProfit2(int[] p, int fee) {
        if(p.length<2) return 0;
        Integer min = null, rs = 0;
        for(int i=0;i<p.length;i++){
            if(min==null||p[i]+fee<min)
                min = p[i]+fee;
            else{
                if(p[i]>min){
                    if(i<p.length-1&&p[i]-p[i+1]>=fee){
                        rs += p[i]-min;
                        min = null;
                    }
                }
            }
        }
        if(min!=null){
            int max = -1;
            for(int j=p.length-1;p[j]+fee!=min;j--){
                max = Math.max(p[j],max);
            }
            if(max-min>0) rs += max-min;
        }
        return rs;
    }

    // This is DP problem
    // it looks like the best time to buy and sell with cooldown
    // problem in some way...(also the Best Time To Buy And Sell II)
    // accepted..
    public int maxProfit4(int[] p, int fee){
        int[] buy = new int[p.length], sell = new int[p.length];
        buy[0] = -p[0]-fee;
        for(int i=1;i<p.length;i++){
            sell[i] = Math.max(sell[i-1], p[i]+buy[i-1]);
            buy[i] = Math.max(buy[i-1], sell[i-1]-p[i]-fee);
        }
        return sell[p.length-1];
    }

    // Do it in-place
    // Accepted..
    public int maxProfit(int[] p, int fee){
        if(p.length<2) return 0;
        int buy = -p[0]-fee, sell = 0;
        for(int i=1;i<p.length;i++){
            int tm = sell;
            // notice that these two var can't be updated for the same time,
            // so we don't need to backup the sell, though it it safe to
            // do that.
            sell = Math.max(sell, p[i]+buy);
            buy = Math.max(buy, tm-p[i]-fee);
        }
        return sell;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maxProfit(new int[]{2,2,1,1,5,5,3,1,5,4},2));// 4
    }
}
