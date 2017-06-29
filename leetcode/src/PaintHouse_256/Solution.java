package PaintHouse_256;

/**
 * There are a row of n houses, each house can be painted with one of the three colors:
 * red, blue or green. The cost of painting each house
 * with a certain color is different.
 * You have to paint all the houses such that
 * no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is
 * represented by a n x 3 cost matrix. For example,
 * costs[0][0] is the cost of painting house 0 with color red;
 * costs[1][2] is the cost of painting house 1 with color green,
 * and so on... Find the minimum cost to paint all houses.
 *
 * Note:
 * All costs are positive integers.
 */
public class Solution {
    // dp
    // O(1) space solution
    public int minCost(int[][] costs) {
        if(costs.length==0) return 0;
        int red = costs[0][0],blue = costs[0][1],green = costs[0][2];
        for(int i=1;i<costs.length;i++){
            int tmred = red,tmblue = blue;
            red = Math.min(blue,green)+costs[i][0];
            blue = Math.min(tmred,green)+costs[i][1];
            green = Math.min(tmblue,tmred)+costs[i][2];
        }
        return Math.min(blue,Math.min(red,green));
    }

    private int mincost = Integer.MAX_VALUE;

    // naive brute force O(3^n) solution
    public int minCost2(int[][] costs){
        if(costs.length==0) return 0;
        helper(costs,0,-1,0);
        return mincost;
    }

    private void helper(int[][] costs,int position,int prev,int cost){
        if(position==costs.length) mincost = Math.min(mincost,cost);
        for(int i=0;i<3;i++)
            if(i!=prev)
                helper(costs,position+1,i,cost+costs[position][i]);
    }
}
