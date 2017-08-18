package MinimumMovesToEqualElementsII_462;

import java.util.Arrays;

/**
 * Given a non-empty integer array,
 * find the minimum number of moves required to
 * make all array elements equal,
 * where a move is incrementing a selected element
 * by 1 or decrementing a selected element by 1.
 *
 * You may assume the array's length is at most 10,000.
 *
 * Example:
 * Input:
 * [1,2,3]
 * Output:
 * 2
 *
 * Explanation:
 * Only two moves are needed (remember each move increments or decrements one element):
 * [1,2,3]  =>  [2,2,3]  =>  [2,2,2]
 */
public class Solution {
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int hi = nums.length-1,lo = 0,rs = 0;
        while(hi>lo)
            rs+=nums[hi--]-nums[lo++];
        return rs;
    }

    // use quick select to find median and then find min moves.
    // explanation:
    // https://leetcode.com/problems/
    // minimum-moves-to-equal-array-elements-ii/solution/#approach-3-using-sorting-accepted
}
