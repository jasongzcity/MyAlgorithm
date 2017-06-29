package PaintHouseII_265;

/**
 * There are a row of n houses, each house can be painted with one of the k colors.
 * The cost of painting each house with a certain color is different.
 * You have to paint all the houses such that no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is
 * represented by a n x k cost matrix. For example,
 * costs[0][0] is the cost of painting house 0 with color 0;
 * costs[1][2] is the cost of painting house 1 with color 2, and so on...
 * Find the minimum cost to paint all houses.
 *
 * Note:
 * All costs are positive integers.
 *
 * Follow up:
 * Could you solve it in O(nk) runtime?
 */
public class Solution {
    // same solution with PaintHouse(#256)
    // O(nk) space
    public int minCostII(int[][] costs) {
        if(costs.length==0) return 0;
        int houses = costs.length,k = costs[0].length;
        int[][] dp = new int[houses][k];
        for(int i=0;i<k;i++) dp[0][i] = costs[0][i];
        for(int i=1;i<houses;i++){
            for(int j=0;j<k;j++){
                int min = Integer.MAX_VALUE;
                for(int p=0;p<k;p++)
                    if(p!=j&&dp[i-1][p]<min)
                        min = dp[i-1][p];
                dp[i][j] = min+costs[i][j];
            }
        }
        int rs = Integer.MAX_VALUE;
        for(int i:dp[houses-1])
            if(i<rs)
                rs = i;
        return rs;
    }

    // leetcode 3ms solution .....
    public int minCostII2(int[][] costs) {
        // Explanation: dp[i][j] represents the min paint cost from house 0 to house i
        // when house i use color j;
        // The formula will be
        // dp[i][j] = Math.min(any k!= j| dp[i-1][k]) + costs[i][j].
        // O(nk) with space: O(1)

        // no need an array to represent dp[i][j],
        // we only need to know the min cost to the previous house of
        // any color and if the color j is used on previous house to get prev min cost,
        // use the second min cost that are not using color j on the previous house.
        // So I have three variable to record: prevMin, prevMinColor, prevSecondMin.
        // and the above formula will be translated into:
        // dp[currentHouse][currentColor] =
        // (currentColor == prevMinColor? prevSecondMin: prevMin) +
        // costs[currentHouse][currentColor].
        if (costs.length == 0) return 0;

        int n = costs.length, k = costs[0].length;
        if (k == 1) return costs[0][0];

        int prevMin = 0, prevMinInd = -1, prevSecMin = 0;
        for (int i = 0; i < n; i++) {
            int min = Integer.MAX_VALUE, minInd = -1, secMin = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                int val = costs[i][j] + (j == prevMinInd ? prevSecMin : prevMin);
                if (minInd < 0) {
                    min = val;
                    minInd = j;
                } else if (val < min) {
                    secMin = min;
                    min = val;
                    minInd = j;
                } else if (val < secMin) {
                    secMin = val;
                }
            }
            prevMin = min;
            prevMinInd = minInd;
            prevSecMin = secMin;
        }
        return prevMin;
    }

    // 2ms...
    public int minCostII3(int[][] costs) {
        if(costs == null || costs.length == 0) {
            return 0;
        }
        if (costs.length == 1 && costs[0].length == 1) {
            return costs[0][0];
        }
        if (costs[0].length == 1) {
            return -1;
        }
        int[] prevMins = new int[]{0, -1, 0, -1};

        for (int i = 0; i < costs.length; i++) {
            prevMins = helper(prevMins, costs[i]);
        }
        return prevMins[0];
    }

    private static int[] helper(int[] prevMins, int[] cost) {
        int min1 = 0;
        int id1 = -1;
        int min2 = 0;
        int id2 = -1;
        for (int i = 0; i < cost.length; i++) {
            int curCost = 0;
            if (prevMins[1] == -1) {
                curCost = cost[i];
            } else if (prevMins[1] == i) {
                curCost = cost[i] + prevMins[2];
            } else {
                curCost = cost[i] + prevMins[0];
            }
            if (id1 == -1 || curCost < min1) {
                min2 = min1;
                id2 = id1;
                min1 = curCost;
                id1 = i;

            } else if (id2 == -1 || curCost <min2) {
                min2 = curCost;
                id2 = i;
            }
        }
        return new int[]{min1, id1, min2, id2};
    }
}
