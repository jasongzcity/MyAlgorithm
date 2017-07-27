package RandomPickIndex_398;

import java.util.Random;

/**
 * Given an array of integers with possible duplicates,
 * randomly output the index of a given target number.
 * You can assume that the given target number must exist in the array.
 *
 * Note:
 * The array size can be very large.
 * Solution that uses too much extra space will not pass the judge.
 *
 * Example:
 * int[] nums = new int[] {1,2,3,3,3};
 * Solution solution = new Solution(nums);
 *
 * // pick(3) should return either index 2, 3, or 4 randomly.
 * // Each index should have equal probability of returning.
 * solution.pick(3);
 *
 * // pick(1) should return 0. Since in the array only nums[0] is equal to 1.
 * solution.pick(1);
 */
public class Solution {
    private int[] a;
    private Random rand = new Random();

    public Solution(int[] nums) { this.a = nums; }

    public int pick(int target) {
        int count = 0,index = 0;
        for(int i=0;i<a.length;i++)
            if(a[i]==target&&rand.nextInt(++count)==0) // notice range [0,count)
                index = i;
        return index;
    }
}
