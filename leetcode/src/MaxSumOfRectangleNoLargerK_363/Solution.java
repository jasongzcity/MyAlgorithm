package MaxSumOfRectangleNoLargerK_363;

import java.util.TreeSet;

/**
 * Given a non-empty 2D matrix matrix and an integer k,
 * find the max sum of a rectangle in the matrix such that its sum
 * is no larger than k.
 *
 * Example:
 * Given matrix = [
 * [1,  0, 1],
 * [0, -2, 3]
 * ]
 * k = 2
 * The answer is 2. Because the sum of rectangle
 * [
 * [0, 1],
 * [-2, 3]
 * ] is 2 and 2 is the max number no larger than k (k = 2).
 *
 * Note:
 * 1. The rectangle inside the matrix must have an area > 0.
 * 2. What if the number of rows is much larger than the number of columns?
 */
public class Solution {
    // Accepted java solution on leetcode
    // Its undone!!!
    public int maxSumSubmatrix(int[][] matrix, int k) {
        //2D Kadane's algorithm + 1D maxSum problem with sum limit k
        //2D subarray sum solution

        //boundary check
        if(matrix.length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;
        int result = Integer.MIN_VALUE;

        //outer loop should use smaller axis
        //now we assume we have more rows than cols, therefore outer loop will be based on cols
        for(int left = 0; left < n; left++){
            //array that accumulate sums for each row from left to right
            int[] sums = new int[m];
            for(int right = left; right < n; right++){
                //update sums[] to include values in curr right col
                for(int i = 0; i < m; i++){
                    sums[i] += matrix[i][right];
                }

                //we use TreeSet to help us find the rectangle with maxSum <= k with O(logN) time
                TreeSet<Integer> set = new TreeSet<>();
                //add 0 to cover the single row case
                set.add(0);
                int currSum = 0;

                for(int sum : sums){
                    currSum += sum;
                    //we use sum subtraction (curSum - sum) to get the subarray with sum <= k
                    //therefore we need to look for the smallest sum >= currSum - k
                    Integer num = set.ceiling(currSum - k);
                    if(num != null) result = Math.max( result, currSum - num );
                    set.add(currSum);
                }
            }
        }

        return result;
    }
}
