package Triangle_120;

import java.util.List;

/**
 * Given a triangle, find the minimum path sum from top to bottom.
 * Each step you may move to adjacent numbers on the row below.
 *
 * For example, given the following triangle
 *
 * [
 *  [2],
 *  [3,4],
 *  [6,5,7],
 *  [4,1,8,3]
 * ]
 * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 *
 * Note:
 * Bonus point if you are able to do this using only O(n) extra space,
 * where n is the total number of rows in the triangle.
 */
public class Solution {
    // bottom-up dp-like
    public static int minimumTotal(List<List<Integer>> t) {
        int size = t.size();
        int[] dp = new int[size];
        List<Integer> l = t.get(size-1);
        for(int i=0;i<size;i++) dp[i] = l.get(i);

        for(int i=size-1;i>0;i--){
            List<Integer> upper = t.get(i-1);
            int prev = dp[0];
            for(int j=0;j<upper.size();j++){
                int cur = dp[j+1];
                dp[j] = Math.min(prev,cur)+upper.get(j);
                prev = cur;
            }
        }
        return dp[0];
    }

    // similar top-down solution can be found easily.
}
